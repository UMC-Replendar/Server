package Umc.replendar.user.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MajorDtoReq {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMajorReq {
        private Long schoolId;
        private String majorName;
    }
}
