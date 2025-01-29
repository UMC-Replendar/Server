package Umc.replendar.activitylog.controller;

import Umc.replendar.activitylog.dto.res.ActivityLogRes;
import Umc.replendar.activitylog.service.ActivityService;
import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final JwtTokenProvider jwtTokenProvider;

//    @Operation(summary = "활동 로그 조회 API", description = "활동 로그 조회 API")
//    @GetMapping("")
//    public ActivityLogRes. getActivityLog() {
//        return "activity log";
//    }

    @Operation(summary = "과제 활동 로그 공유 수락 응답 API", description = "과제 활동 로그 공유 수락 응답 API")
    @PatchMapping("/share/{logId}")
    public ApiResponse<ActivityLogRes.shareActivity> shareActivityLog(@PathVariable Long logId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return activityService.shareActivityLog(logId, userId);
    }

}
