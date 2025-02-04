package Umc.replendar.user.service;

import Umc.replendar.assignment.entity.Status;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.friend.repository.FriendRepository;
import Umc.replendar.global.util.AmazonS3Util;
import Umc.replendar.global.util.CookieUtil;
import Umc.replendar.user.converter.UserConverter;
import Umc.replendar.user.dto.req.UserDtoReq;
import Umc.replendar.user.dto.res.KakaoUserInfoResponseDto;
import Umc.replendar.user.dto.res.UserDtoRes;
import Umc.replendar.user.entity.Theme;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AssignmentRepository assignmentRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final AmazonS3Util amazonS3Util;

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
                            .name(userInfo.getKakaoAccount().getProfile().getNickName())
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
    // 테마 변경
    public void updateTheme(Long userId, String themeName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾지 못했습니다."));

        try {
            user.setTheme(Theme.valueOf(themeName.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 테마입니다.");
        }

        userRepository.save(user);
    }
    // 유저 상태 메시지 변경
    public void updateStatusMessage(Long userId, String statusMessage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾지 못했습니다."));
        user.setStatusMessage(statusMessage);
        userRepository.save(user);
    }
    //프로필 정보 조회
    public UserDtoRes.UserProfileRes getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾지 못했습니다."));

        String profileImageUrl = amazonS3Util.getProfilePath(userId);
        
        // 둘 다 데이터 개수가 30개 이하일 것으로 예상되어 단순 조회로 함
        int friendCount = friendRepository.findAllByUserIdOrFriendId(userId, userId).size();
        int ongoingTasks = assignmentRepository.findAllByUserAndStatusOrderByDueDate(user, Status.ONGOING, Pageable.unpaged()).getContent().size();

        return UserDtoRes.UserProfileRes.builder()
                .nickname(user.getNickname())
                .statusMessage(user.getStatusMessage())
                .friendCount(friendCount)
                .ongoingTasks(ongoingTasks)
                .profileImageUrl(profileImageUrl)
                .build();
    }
    // 닉네임 중복 확인
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
