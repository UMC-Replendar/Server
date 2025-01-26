package Umc.replendar.common.security;


import Umc.replendar.apiPayload.code.exception.handler.UserHandler;
import Umc.replendar.apiPayload.code.status.ErrorStatus;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userPk)  {

        User user = userRepository.findById(Long.parseLong(userPk))
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        return new CustomUserDetail(user);	// 위에서 생성한 CustomUserDetails Class
    }
}

