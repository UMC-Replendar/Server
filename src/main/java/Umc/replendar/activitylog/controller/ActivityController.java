package Umc.replendar.activitylog.controller;

import Umc.replendar.activitylog.dto.res.ActivityLogRes;
import Umc.replendar.activitylog.service.ActivityService;
import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.common.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "히스토리 조회 API", description = "히스토리 조회 API")
    @GetMapping("")
    public Page<ActivityLogRes.getHistoryRes> getActivityLog(@RequestParam(defaultValue = "1") int page,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        return activityService.getActivityLog(userId, adjustedPageable);
    }

    @Operation(summary = "과제 활동 로그 공유 수락 응답 API", description = "과제 활동 로그 공유 수락 응답 API")
    @PatchMapping("/share/accept/{logId}")
    public ApiResponse<AssignmentRes.assLogRes> shareAcceptLog(@PathVariable Long logId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return activityService.shareActivityLog(logId, userId);
    }

    @Operation(summary = "과제 활동 로그 공유 거절 응답 API", description = "과제 활동 로그 공유 거절 응답 API")
    @PatchMapping("/share/reject/{logId}")
    public ApiResponse<ActivityLogRes.shareActivity> shareRejectLog(@PathVariable Long logId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return activityService.shareRejectLog(logId, userId);
    }





}
