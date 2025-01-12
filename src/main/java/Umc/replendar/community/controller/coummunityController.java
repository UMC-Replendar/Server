package Umc.replendar.community.controller;

import Umc.replendar.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/community")
public class coummunityController {
    public void test() {
        System.out.println("coummunityController");
    }

    String t = "결과값";

    @Operation(summary = "안녕하세요", description = "테스트 api입니다")
    @GetMapping("/test")
    public ApiResponse testApi() {
        return ApiResponse.onSuccess(t);
    }
}
