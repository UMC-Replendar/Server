package Umc.replendar.activitylog.service;

import Umc.replendar.activitylog.dto.res.ActivityLogRes;
import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityService {
    ApiResponse<AssignmentRes.assLogRes> shareActivityLog(Long logId, Long userId);

    ApiResponse<ActivityLogRes.shareActivity> shareRejectLog(Long logId, Long userId);

    Page<ActivityLogRes.getHistoryRes> getActivityLog(Long userId, Pageable pageable);
}
