package Umc.replendar.user.dto.req;

import lombok.*;

public class SchoolDtoReq {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateSchoolReq {
        private String schoolName;
    }
}
