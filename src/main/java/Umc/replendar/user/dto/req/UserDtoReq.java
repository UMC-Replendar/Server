package Umc.replendar.user.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDtoReq {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpReq {
        String email;
        String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginReq {
        String email;
    }
}
