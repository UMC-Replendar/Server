package Umc.replendar.user.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.user.dto.req.MajorDtoReq;
import Umc.replendar.user.dto.res.MajorDtoRes;
import Umc.replendar.user.service.MajorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
public class MajorController {

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
}
