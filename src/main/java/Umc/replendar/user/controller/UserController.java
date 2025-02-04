package Umc.replendar.user.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.user.dto.req.UserDtoReq;
import Umc.replendar.user.dto.res.UserDtoRes;
import Umc.replendar.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ApiResponse<UserDtoRes.UserLoginRes> login(@RequestBody UserDtoReq.LoginReq loginDto, HttpServletRequest request, HttpServletResponse response) {

        return ApiResponse.onSuccess(userService.login(request,response,loginDto));
    }
    @Operation(summary = "테마 변경 API", description = "사용자의 저장된 테마 정보를 변경")
    @PatchMapping("/theme")
    public ApiResponse<String> updateTheme(@RequestParam String theme) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        userService.updateTheme(userId, theme);
        return ApiResponse.onSuccess("테마가 " + theme + "으로 변경되었습니다.");
    }

}
