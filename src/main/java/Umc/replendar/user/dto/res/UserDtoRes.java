package Umc.replendar.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class UserDtoRes {

    @Data
    @AllArgsConstructor
    @Builder
    public static class UserLoginRes {
        private Long id;
        private String email;
        private String accessToken;
        private String nickName;
        private String theme;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class UserProfileRes {
        private String nickname;
        private String statusMessage;
        private int friendCount;
        private int ongoingTasks;
    }
}
