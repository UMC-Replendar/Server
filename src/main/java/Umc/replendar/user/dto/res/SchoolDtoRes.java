package Umc.replendar.user.dto.res;

import Umc.replendar.major.entity.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SchoolDtoRes {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchoolSimpleRes {
        private Long id;
        private String schoolName;

        public static SchoolSimpleRes fromEntity(School school) {
            SchoolSimpleRes dto = new SchoolSimpleRes();
            dto.setId(school.getId());
            dto.setSchoolName(school.getSchoolName());
            return dto;
        }
    }
}
