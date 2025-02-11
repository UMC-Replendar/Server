package Umc.replendar.major.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.major.dto.req.LectureReq;
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



    @Operation(summary = "학과 과제 조회 - 정렬 기준 - 학년별 API, 기본", description = "특정 학년에 대한 본인 학과의 과제 조회")
    @GetMapping({"/lectures", "/lectures/{academicYear}"})
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLectures(
            @PathVariable(required = false) Long academicYear) {
        long id = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectureAssignment(id, academicYear);
    }

    //교수명, 강좌명, 과제명 , 마감일 만들기
    //교수명
    @Operation(summary = "학과 과제 조회 - 정렬 기준 - 교수명 API", description = "교수명으로 정렬")
    @GetMapping("/lectures/sort/professor")
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLecturesSortByProfessor(@RequestParam String sort, @RequestParam(required = false) String academicYear, @RequestParam(required = false) Long majorId ) {
        long id = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectureAssignmentSort(id, "professor", sort, academicYear, majorId);
    }

    @Operation(summary = "학과 과제 조회 - 정렬 기준 - 강좌명 API", description = "강좌명으로 정렬")
    @GetMapping("/lectures/sort/registration")
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLecturesSortByLectureName(@RequestParam String sort, @RequestParam(required = false) String academicYear, @RequestParam(required = false) Long majorId) {
        long id = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectureAssignmentSort(id, "lectureName", sort, academicYear, majorId);
    }

    @Operation(summary = "학과 과제 조회 - 정렬 기준 - 과제명 API", description = "과제명으로 정렬")
    @GetMapping("/lectures/sort/assignment")
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLecturesSortByTitle(@RequestParam String sort, @RequestParam(required = false) String academicYear, @RequestParam(required = false) Long majorId) {
        long id = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectureAssignmentSort(id, "title", sort, academicYear, majorId);
    }

    @Operation(summary = "학과 과제 조회 - 정렬 기준 - 마감일 API", description = "마감일로 정렬")
    @GetMapping("/lectures/sort/due")
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLecturesSortByDueDate(@RequestParam String sort, @RequestParam(required = false) String academicYear, @RequestParam(required = false) Long majorId) {
        long id = jwtTokenProvider.getUserIdFromToken();

        return majorService.getLectureAssignmentSort(id, "dueDate", sort, academicYear, majorId);
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

    @Operation(summary = "학과 과제 생성 API", description = "학과 과제 생성")
    @PostMapping("/lectures")
    public ApiResponse<String> createLectureAssignment(@RequestBody LectureReq.LectureAssignmentPostReq request) {
        long userId = jwtTokenProvider.getUserIdFromToken();

        return majorService.createLectureAssignment(userId, request);
    }


}
