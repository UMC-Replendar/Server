package Umc.replendar.assignment.service;

import Umc.replendar.activitylog.entity.Action;
import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.activitylog.repository.ActivityRepository;
import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.reqDto.AssignmentReq;
import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.assignment.entity.GeneralSettings;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.friend.entity.Friend;
import Umc.replendar.friend.repository.FriendRepository;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final FriendRepository friendRepository;

    //과제추가 API
    //과제를 추가하면 친구들에게 알림이 가게 구현해야함
    //공유할 친구 USERID를 따로 다 찾은 후
    //공유할 친구 수 만큼 반복문을 돌려서 과제를 추가해준다. - 이 때 진행상태 WAIT
    //과제를 추가할 때마다 친구 ID의 활동 로그를 추가한다. - 이 때 액션 과제 공유 SHARE, 확인여부 UNCHECK
    public ApiResponse<String> createAssignment(AssignmentReq.CreateReqDto reqDto){
        System.out.println(reqDto.getUserId());
        //본인 과제 등록
        User user = userRepository.findById(reqDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Assignment assignment = Assignment.builder()
                .user(user)
                .title(reqDto.getTitle())
                .due_date(reqDto.getEndDate())
                .notification("ON".equalsIgnoreCase(reqDto.getNotification()) ? GeneralSettings.ON : GeneralSettings.OFF)
                .visibility("ON".equalsIgnoreCase(reqDto.getNotification()) ? GeneralSettings.ON : GeneralSettings.OFF)
                .memo(reqDto.getMemo())
                .completion_time(reqDto.getEndDate())
                .status(Status.ONGOING)
                .build();
        assignmentRepository.save(assignment);

        //과제 등록한 사용자의 친구들 찾기
        List<User> userFriend = friendRepository.findAllByUserId(user.getId()).stream()
                .map(Friend::getFriend)
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

        for(Long shareId : reqDto.getShareIds()){
            User friendUser = userRepository.findById(shareId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
            //친구 과제 등록
            Assignment frAssignment = Assignment.builder()
                    .user(friendUser)
                    .title(reqDto.getTitle())
                    .due_date(reqDto.getEndDate())
                    .notification(GeneralSettings.OFF)
                    .visibility(GeneralSettings.OFF)
                    .memo(reqDto.getMemo())
                    .completion_time(reqDto.getEndDate())
                    .status(Status.WAIT) //대기상태
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

}
