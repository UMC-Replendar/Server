package Umc.replendar.user.service;

import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.global.util.CookieUtil;
import Umc.replendar.user.converter.UserConverter;
import Umc.replendar.user.dto.req.UserDtoReq;
import Umc.replendar.user.dto.res.KakaoUserInfoResponseDto;
import Umc.replendar.user.dto.res.UserDtoRes;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

//    public User signup(UserDtoReq.SignUpReq signUpDto) {
//
//        User user = User.builder()
//                .email(signUpDto.getEmail())
//                .password(passwordEncoder.encode(signUpDto.getPassword())) // 암호화
//                .receiveAds(signUpDto.getReceiveAds())
//                .build();
//        //회원가입
//        userRepository.save(user);
//        //유저 프로필 추가
//        UserProfile userProfile = UserProfile.createWithUser(user);
//        userProfileRepository.save(userProfile);
//
//        return user;
//    }

    public UserDtoRes.UserLoginRes login(HttpServletRequest request, HttpServletResponse response, UserDtoReq.LoginReq loginDto) {

        String email = loginDto.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾지 못했습니다."));

//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }

//        if(user.getType() == Type.KAKAO){
//            throw new IllegalArgumentException("카카오 로그인 유저입니다.");
//        }

//        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("해당 유저의 프로필이 없습니다."));

        String accessToken = jwtTokenProvider.createAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        log.info("login refresh token : {}", refreshToken);

        // 쿠키 저장
        CookieUtil.deleteCookie(request, response, "refreshToken");
        CookieUtil.addCookie(response, "refreshToken", refreshToken, JwtTokenProvider.REFRESH_TOKEN_VALID_TIME_IN_COOKIE);

        return UserConverter.signInRes(user, accessToken, user.getNickname());
    }


    public User kakaoSignup(KakaoUserInfoResponseDto userInfo) {
        //이미 회원가입한 이메일이 있다면 user 리턴
        //회원가입된게 없다면 회원가입 및 유저프로필 생성 후 유저 리턴
        return userRepository.findByEmail(userInfo.getKakaoAccount().getEmail())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(userInfo.getKakaoAccount().getEmail())
//                            .type(Type.KAKAO)
                            .build();
                    userRepository.save(newUser);

//                    UserProfile userProfile = UserProfile.builder()
//                            .user(newUser)
//                            .nickName(userInfo.getKakaoAccount().profile.getNickName())
//                            .build();
//                    userProfileRepository.save(userProfile);

                    return newUser;
                });
    }

    public UserDtoRes.UserLoginRes kakaoLogin(HttpServletRequest request, HttpServletResponse response, User user) {

//        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("해당 유저의 프로필이 없습니다."));
//        User user2 = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저의 프로필이 없습니다."));

        String accessToken = jwtTokenProvider.createAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        log.info("login refresh token : {}", refreshToken);

        // 쿠키 저장
        CookieUtil.deleteCookie(request, response, "refreshToken");
        CookieUtil.addCookie(response, "refreshToken", refreshToken, JwtTokenProvider.REFRESH_TOKEN_VALID_TIME_IN_COOKIE);

        return UserConverter.signInRes(user, accessToken, user.getNickname());
    }
}
