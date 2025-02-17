package Umc.replendar.major.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.major.service.LectureAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lecture-assignments")
@RequiredArgsConstructor
public class LectureAssignmentController {

    private final JwtTokenProvider jwtTokenProvider;
    private final LectureAssignmentService lectureAssignmentService;

    @Operation(summary = "특정 강의의 과제 목록 조회 API", description = "강의 ID를 기반으로 해당 강의의 과제 목록을 조회")
    @GetMapping("/{lectureId}")
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getAssignmentsByLectureId(@PathVariable Long lectureId) {

        long userId = jwtTokenProvider.getUserIdFromToken();
        List<LectureAssignmentRes.LectureAssignmentGetRes> assignments = lectureAssignmentService.getAssignmentsByLectureId(userId, lectureId);

        return ApiResponse.onSuccess(assignments);
    }
}
