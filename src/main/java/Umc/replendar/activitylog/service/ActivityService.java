package Umc.replendar.activitylog.service;

import Umc.replendar.activitylog.dto.res.ActivityLogRes;
import Umc.replendar.apiPayload.ApiResponse;

public interface ActivityService {
    ApiResponse<ActivityLogRes.shareActivity> shareActivityLog(Long logId, Long userId);
}
