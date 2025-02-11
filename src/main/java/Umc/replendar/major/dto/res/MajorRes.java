package Umc.replendar.major.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MajorRes {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MajorRes2{
        private Long majorId;

        private String majorName;
    }
}
