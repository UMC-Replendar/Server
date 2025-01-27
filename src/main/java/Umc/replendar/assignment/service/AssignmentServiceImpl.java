package Umc.replendar.assignment.service;

import Umc.replendar.activitylog.entity.Action;
import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.activitylog.repository.ActivityRepository;
import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.apiPayload.code.status.SuccessStatus;
import Umc.replendar.assignment.converter.AssToDto;
import Umc.replendar.assignment.dto.reqDto.AssignmentReq;
import Umc.replendar.assignment.dto.reqDto.SaveType;
import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.assignment.entity.*;
import Umc.replendar.assignment.repository.AssNotifyCycleRepository;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.assignment.repository.ShareRepository;
import Umc.replendar.friend.entity.Friend;
import Umc.replendar.friend.repository.FriendRepository;
import Umc.replendar.global.function.TaskTimer;
import Umc.replendar.user.entity.Active;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static Umc.replendar.assignment.converter.AssToDto.toMainTopDto;
import static Umc.replendar.assignment.converter.AssToDto.toShareUserDto;


@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final FriendRepository friendRepository;
    private final AssNotifyCycleRepository assNotifyCycleRepository;
    private final ShareRepository shareRepository;

    //과제추가 API
    //과제를 추가하면 친구들에게 알림이 가게 구현해야함
    //공유할 친구 USERID를 따로 다 찾은 후
    //공유할 친구 수 만큼 반복문을 돌려서 과제를 추가해준다. - 이 때 진행상태 WAIT
    //과제를 추가할 때마다 친구 ID의 활동 로그를 추가한다. - 이 때 액션 과제 공유 SHARE, 확인여부 UNCHECK
    public ApiResponse<String> createAssignment(Long userId, AssignmentReq.CreateReqDto reqDto){

        System.out.println(userId);
        //본인 과제 등록
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Assignment assignment = Assignment.builder() //과제등록
                .user(user)
                .title(reqDto.getTitle())
                .due_date(reqDto.getEndDate())
                .notification("ON".equalsIgnoreCase(reqDto.getNotification()) ? GeneralSettings.ON : GeneralSettings.OFF)
                .visibility("ON".equalsIgnoreCase(reqDto.getNotification()) ? GeneralSettings.ON : GeneralSettings.OFF)
                .memo(reqDto.getMemo())
                .status(Status.ONGOING)
                .favorite(reqDto.getFavorite())
                .build();
        assignmentRepository.save(assignment);

        //완료시간에서 알림주기를 계산한 후
        //과제 알림 주기 테이블에 시간과 알림주기를 저장
        for(NotifyCycle notifyCycle : reqDto.getNotifyCycle()){
            AssNotifyCycle assNotifyCycle = AssNotifyCycle.builder()
                    .assignment(assignment)
                    .notifyCycle(notifyCycle)
                    .notifyTime(TaskTimer.notifyCycle(assignment.getDueDate(), notifyCycle))
                    .build();
            assNotifyCycleRepository.save(assNotifyCycle);
        }


        //과제 등록한 사용자의 친구들 찾기, 찾을때 friend나 user에 자기아이디가 있는지 확인하기
        List<User> userFriend = friendRepository.findAllByUserIdOrFriendId(user.getId(), user.getId()).stream()
                .map(friend -> friend.getFriend().getId().equals(user.getId()) ? friend.getUser() : friend.getFriend())
                .toList();

        for(User friend : userFriend){
            ActivityLog activityLog = ActivityLog.builder()
                    .user(friend) //친구 활동 로그에 추가하는거임
                    .friend(user) //타깃은 본인
                    .assignment(assignment)
                    .action(Action.ADD_ASS) //친구가 과제를 올렸습니다.
                    .isCheck(Check.UNCHECK) //확인안함
                    .build();
            activityRepository.save(activityLog);
        }

        //과제 등록할 때 공유했다면 반복문 돌리기
        for(Long shareId : reqDto.getShareIds()){
            if(Objects.equals(shareId, userId)){
                return ApiResponse.onFailure("INVALID_REQUEST", "자기 자신에게 공유할 수 없습니다.", null);
            }

            User friendUser = userRepository.findById(shareId).orElseThrow(() -> new IllegalArgumentException("과제 공유 받는 사용자가 존재하지 않는 사용자입니다."));
            //과제 공유한 아이디 저장해야함(수정 때 사용해야하기에)
            Share share = Share.builder()
                    .user(friendUser)
                    .assignment(assignment).build();
            shareRepository.save(share);

            //친구 과제 등록
            Assignment frAssignment = Assignment.builder()
                    .user(friendUser)
                    .title(reqDto.getTitle())
                    .due_date(reqDto.getEndDate())
                    .notification(GeneralSettings.OFF)
                    .visibility(GeneralSettings.OFF)
                    .memo(reqDto.getMemo())
                    .status(Status.WAIT) //대기상태
                    .favorite(Active.INACTIVE)
                    .build();
            assignmentRepository.save(frAssignment);

            //활동 로그 추가
            ActivityLog activityLog = ActivityLog.builder()
                    .user(friendUser)
                    .friend(user)
                    .assignment(frAssignment)
                    .action(Action.SHARE) //공유
                    .isCheck(Check.UNCHECK) //확인안함
                    .build();
            activityRepository.save(activityLog);
        }
        return ApiResponse.onSuccess("과제가 등록되었습니다.");
    }

    //과제 조회 api
    //탑 10개 까지 조회하면 됨
    //진행중인 과제는 status가 ONGOING
    //과제의 마감시간 - 현재시간을 해서 갖다주기
    @Override
    public ApiResponse<List<AssignmentRes.assMainTopRes>> getAssMainTopAssignment(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));
        System.out.println(user.getId());
        List<Assignment> assignments = assignmentRepository.findTop10ByUserAndStatusOrderByDueDate(user, Status.ONGOING);
        for (Assignment assignment : assignments) {
            System.out.println(assignment.getTitle());
        }
        //과제의 마감시간 - 현재시간을 해서 갖다주기
        return ApiResponse.onSuccess(toMainTopDto(assignments));
    }

    @Override
    public ApiResponse<String> updateAssignment(AssignmentReq.updateReqDto reqDto) {

        Assignment assignment = assignmentRepository.findById(reqDto.getAssId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과제입니다."));

        assignment.setTitle(reqDto.getTitle());
        assignment.setDueDate(reqDto.getEndDate());
        assignment.setMemo(reqDto.getMemo());
        assignment.setNotification(String.valueOf("ON".equalsIgnoreCase(reqDto.getNotification()) ? GeneralSettings.ON : GeneralSettings.OFF));
        assignment.setVisibility("ON".equalsIgnoreCase(reqDto.getVisibility()) ? GeneralSettings.ON : GeneralSettings.OFF);
        assignment.setFavorite(reqDto.getFavorite());
        assignmentRepository.save(assignment);

        //수정 시 공유할 친구 삭제는 불가능함
        //추가만 가능하기에 이전에 공유했던 친구에게 공유가 중첩되면 안되기에 중첩되는 사람을 필터 후 새로운 사람에게만 과제 공유
        reqDto.getShareIds().removeAll(
                shareRepository.findAllByAssignment(assignment).stream()
                        .map(share -> share.getUser().getId()).toList());

        for(Long shareId : reqDto.getShareIds()){
            User friendUser = userRepository.findById(shareId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

            Share share = Share.builder()
                    .user(friendUser)
                    .assignment(assignment).build();
            shareRepository.save(share);

            Assignment frAssignment = Assignment.builder()
                    .user(friendUser)
                    .title(reqDto.getTitle())
                    .due_date(reqDto.getEndDate())
                    .notification(GeneralSettings.OFF)
                    .visibility(GeneralSettings.OFF)
                    .memo(reqDto.getMemo())
                    .status(Status.WAIT)
                    .favorite(Active.INACTIVE)
                    .build();
            assignmentRepository.save(frAssignment);

            ActivityLog activityLog = ActivityLog.builder()
                    .user(friendUser)
                    .friend(assignment.getUser())
                    .assignment(frAssignment)
                    .action(Action.SHARE)
                    .isCheck(Check.UNCHECK)
                    .build();
            activityRepository.save(activityLog);
        }

        return ApiResponse.onSuccess("과제가 수정되었습니다.");
    }

    //활동로그에서 해당 과제가 다 삭제되는지 확인해야함 - 삭제됨
    @Override
    public ApiResponse<String> deleteAssignment(Long assId) {

        Assignment assignment = assignmentRepository.findById(assId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과제입니다."));

        assignmentRepository.delete(assignment);

        return ApiResponse.onSuccess("과제가 삭제되었습니다.");
    }

    //과제 상세보기
    @Override
    public ApiResponse<AssignmentRes.assDetailRes> getAssDetail(Long assId) {
        Assignment assignment = assignmentRepository.findById(assId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과제입니다."));
        List<Long> shareFriendList = shareRepository.findAllByAssignment(assignment).stream()
                .map(share -> share.getUser().getId()).toList();

        List<String> notifyCycleList = assNotifyCycleRepository.findAllByAssignment(assignment).stream().
                map(assNotifyCycle -> String.valueOf(assNotifyCycle.getNotifyCycle())).toList();

        List<User> shareFriendEntityList = shareFriendList.stream().
                map(shareFriend -> userRepository.findById(shareFriend).
                        orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."))).toList();

        return ApiResponse.of(SuccessStatus._OK,AssToDto.toDetailDto(assignment, notifyCycleList, shareFriendEntityList));
    }

    //과제 월별로 조회
    //조회시 status ongoing 인것만 조회
    @Override
    public ApiResponse<List<AssignmentRes.assMonthRes>> getAssMonth(Long userId, String month) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        LocalDateTime mont = LocalDateTime.parse(month + "-01T00:00:00.000000");
        LocalDateTime montEnd = mont.plusMonths(1);
        montEnd = montEnd.minusSeconds(1);

        List<Assignment> assignment = assignmentRepository.findAllByUserAndDueDateBetweenAndStatusOrderByDueDate(user, mont, montEnd, Status.ONGOING);

        return ApiResponse.of(SuccessStatus._OK, AssToDto.toMonthDto(assignment));
    }

    //과제 공유할 친구 리스트 조회
    //리스트 조회할 때 user와 friend중 어디에 자기아이디가 있는지 확인하기
    //확인하고 메모 던져주기
    @Override
    public ApiResponse<List<AssignmentRes.assShareRes>> getAssShareFriendList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<AssignmentRes.assShareRes> resList = friendRepository.findAllByUserIdOrFriendId(user.getId(), user.getId()).stream()

                //friend.friend의 id가 user의 id와 같으면 friend의 user를 반환, 아니면 friend를 반환
                .map(friend -> friend.getFriend().getId().equals(user.getId()) ? toShareUserDto(true, friend) : toShareUserDto(false, friend))
                .toList();

        return ApiResponse.of(SuccessStatus._OK, resList);
    }


}
