package Umc.replendar.activitylog.service;

import Umc.replendar.activitylog.dto.res.ActivityLogRes;
import Umc.replendar.activitylog.entity.Action;
import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.activitylog.repository.ActivityLogRepository;
import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static Umc.replendar.assignment.converter.AssToDto.toShareOkDto;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;
    private final AssignmentRepository assignmentRepository;

    //활동유형이 공유인걸 확인 및 isCheck가 false인 경우 true로 변경
    //만약 이미 공유된 활동이라면 이미 공유된 활동이라는 메세지를 반환(ischeck가 true인 경우)
    //활동유형이 공유가 아닌 경우 활동유형이 공유가 아닙니다. 라는 메세지를 반환
    @Override
    public ApiResponse<AssignmentRes.assLogRes> shareActivityLog(Long logId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾지 못했습니다."));

        ActivityLog activityLog = activityLogRepository.findById(logId).orElseThrow(() -> new IllegalArgumentException("활동 로그를 찾지 못했습니다."));

        if(!user.equals(activityLog.getUser())){
            return ApiResponse.onFailure("USER_NOT_MATCH","해당 활동 로그에 대한 권한이 없습니다.",null);
        }

        if(!activityLog.getAction().equals(Action.SHARE)){
            return ApiResponse.onFailure("ACTIVITY_TYPE_NOT_SHARE","활동유형이 공유가 아닙니다.",null);
        }

        if(activityLog.getIsCheck().equals(Check.CHECK)){
            return ApiResponse.onFailure("ALREADY_SHARED","이미 공유된 활동입니다.",null);
        }
        activityLog.setIsCheck(Check.CHECK);
        activityLogRepository.save(activityLog);

        Assignment beforeAss = assignmentRepository.findById(activityLog.getAssignment().getId()).orElseThrow(() -> new IllegalArgumentException("해당 과제를 찾지 못했습니다."));

        beforeAss.setStatus(Status.ONGOING);

        return ApiResponse.onSuccess(toShareOkDto(assignmentRepository.save(beforeAss)));

    }
}
