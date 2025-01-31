package Umc.replendar.user.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.user.dto.res.KakaoUserInfoResponseDto;
import Umc.replendar.user.dto.res.UserDtoRes;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import Umc.replendar.user.service.KakaoService;
import Umc.replendar.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/callback")
    public ApiResponse<UserDtoRes.UserLoginRes> callback(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        //회원가입, 로그인 동시진행
        return ApiResponse.onSuccess(userService.kakaoLogin(request,response, userService.kakaoSignup(userInfo)));
    }

//    @GetMapping("/oauth/kakao")
//    public ApiResponse<String> callback(@RequestParam("code") String code) {
//        //회원가입, 로그인 동시진행
//        return ApiResponse.onSuccess(code);
//    }

    @PostMapping("whoami")
    public Long whoami() {
        Long userIdFromToken = jwtTokenProvider.getUserIdFromToken();

        User user = userRepository.findById(userIdFromToken).orElseThrow(() -> new IllegalArgumentException("유저를 찾지 못했습니다."));
        //회원가입, 로그인 동시진행
        return user.getId();
    }
}