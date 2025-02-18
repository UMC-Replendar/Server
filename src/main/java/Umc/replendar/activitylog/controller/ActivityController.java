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

    @Operation(summary = "히스토리 전체 조회 API", description = "히스토리 전체 조회 API")
    @GetMapping("")
    public Page<ActivityLogRes.getHistoryRes> getActivityLog(@RequestParam(defaultValue = "1") int page,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        return activityService.getActivityLog(userId, adjustedPageable);
    }

    @Operation(summary = "히스토리 필터 - 친구소식 조회 API", description = "히스토리 필터 - 친구소식 조회 API")
    @GetMapping("/friend")
    public ApiResponse<Page<ActivityLogRes.FriendActivityHistoryRes>> getActivityFriendLog(@RequestParam(defaultValue = "1") int page,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        return activityService.getActivityFriendLog(userId, adjustedPageable);
    }

    @Operation(summary = "히스토리 필터 - 과제알림 API", description = "히스토리 필터 - 과제알림 조회 API")
    @GetMapping("/assignment/notify")
    public ApiResponse<Page<ActivityLogRes.getHistoryRes>> getActivityAssignmentNotify(@RequestParam(defaultValue = "1") int page,
                                                                      @PageableDefault(size = 10) Pageable pageable) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        return activityService.getActivityAssignmentNotifyLog(userId, adjustedPageable);
    }


    @Operation(summary = "과제 활동 로그 공유 수락 응답 API", description = "과제 활동 로그 공유 수락 응답 API")
    @PatchMapping("/share/accept/{activityLogId}")
    public ApiResponse<AssignmentRes.assLogRes> shareAcceptLog(@PathVariable Long activityLogId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return activityService.shareActivityLog(activityLogId, userId);
    }

    @Operation(summary = "과제 활동 로그 공유 거절 응답 API", description = "과제 활동 로그 공유 거절 응답 API")
    @PatchMapping("/share/reject/{activityLogId}")
    public ApiResponse<ActivityLogRes.shareActivity> shareRejectLog(@PathVariable Long activityLogId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return activityService.shareRejectLog(activityLogId, userId);
    }

    @Operation(summary = "과제 활동 로그 읽음 처리 API", description = "과제 활동 로그 읽음 처리 API")
    @PatchMapping("/{activityLogId}")
    public ApiResponse<String> checkAssLog(@PathVariable Long activityLogId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return activityService.checkLog(activityLogId, userId);
    }

    @Operation(summary = "과제 알림 로그 읽음 처리 API", description = "과제 알림 로그 읽음 처리 API")
    @PatchMapping("/notify/{assignmentNotifyId}")
    public ApiResponse<String> checkNotifyLog(@PathVariable Long assignmentNotifyId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return activityService.checkNotifyLog(assignmentNotifyId, userId);
    }





}
