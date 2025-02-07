package Umc.replendar.major.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.major.service.MajorService;
import Umc.replendar.user.dto.req.MajorDtoReq;
import Umc.replendar.user.dto.res.MajorDtoRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/major")
@RequiredArgsConstructor
public class majorController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MajorService majorService;

    @Operation(summary = "학과 검색 API", description = "특정 schoolId 내에서 학과(keyword) 검색")
    @GetMapping
    public ApiResponse<List<MajorDtoRes.MajorSimpleRes>> searchMajor(
            @RequestParam Long schoolId,
            @RequestParam(required = false) String keyword
    ) {
        List<MajorDtoRes.MajorSimpleRes> result = majorService.searchMajor(schoolId, keyword);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "학과 등록 API", description = "특정 schoolId에 새로운 학과 등록")
    @PostMapping
    public ApiResponse<MajorDtoRes.MajorSimpleRes> createMajor(
            @RequestBody MajorDtoReq.CreateMajorReq request
    ) {
        MajorDtoRes.MajorSimpleRes response = majorService.createMajor(request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "본인 학과의 학년별 과제 조회 API", description = "특정 학년에 대한 본인 학과의 과제 조회")
    @GetMapping({"/lectures", "/lectures/{academicYear}"})
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLectures(
            @PathVariable(required = false) Long academicYear) {
        long id = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectureAssignment(id, academicYear);
    }

    @Operation(summary = "학과 과제 생성에 필요한 데이터 조회 API", description = "학과 과제 생성에 필요한 데이터 조회")
    @GetMapping("/lectures/get/{lectureAssignmentId}")
    public ApiResponse<LectureAssignmentRes.LectureAssignmentPostRes> getLectureCreateData(@PathVariable Long lectureAssignmentId) {
        long userId = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectureCreateData(userId, lectureAssignmentId);
    }

    @Operation(summary = "강좌 목록 조회 API", description = "강좌 목록 조회")
    @GetMapping({"/lectures/list", "/lectures/list/{academicYear}"})
    public ApiResponse<List<LectureAssignmentRes.LecturesRes>> getLecture(
            @PathVariable(required = false) Long academicYear) {
        long userId = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectures(userId, academicYear);
    }

    @Operation(summary = "학과 소식 조회 API", description = "학과 소식 조회")
    @GetMapping("/lectures/news")
    public ApiResponse<Page<LectureAssignmentRes.LectureNewsRes>> getLectureNews(@RequestParam(defaultValue = "1") int page,
                                                                                 @PageableDefault(size = 10) Pageable pageable) {
        long userId = jwtTokenProvider.getUserIdFromToken();
        Pageable adjustedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        return majorService.getLectureNews(userId,adjustedPageable);
    }


}
