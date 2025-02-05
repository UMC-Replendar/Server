package Umc.replendar.user.dto.req;

import lombok.*;

public class UserDtoReq {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpReq {
        private String nickname;          // 닉네임
        private String statusMessage;     // 상태 메시지 (선택 사항)
        private Long majorId;             // 학과ID
        private int academicYear;      // 학년
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginReq {
        String email;
    }
}
