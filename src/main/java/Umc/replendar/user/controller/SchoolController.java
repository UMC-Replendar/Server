package Umc.replendar.user.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.user.dto.req.SchoolDtoReq;
import Umc.replendar.user.dto.res.SchoolDtoRes;
import Umc.replendar.user.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "학교 검색 API", description = "학교 이름(keyword)이 포함된 학교 목록 검색")
    @GetMapping
    public ApiResponse<List<SchoolDtoRes.SchoolSimpleRes>> searchSchool(@RequestParam(required = false) String keyword) {
        List<SchoolDtoRes.SchoolSimpleRes> result = schoolService.searchSchool(keyword);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "학교 등록 API", description = "새로운 학교 정보 등록")
    @PostMapping
    public ApiResponse<SchoolDtoRes.SchoolSimpleRes> createSchool(@RequestBody SchoolDtoReq.CreateSchoolReq request) {
        SchoolDtoRes.SchoolSimpleRes response = schoolService.createSchool(request);
        return ApiResponse.onSuccess(response);
    }
}
