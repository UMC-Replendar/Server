package Umc.replendar.user.dto.res;

import Umc.replendar.user.entity.Major;
import lombok.*;

public class MajorDtoRes {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MajorSimpleRes {
        private Long id;
        private String majorName;
        private Long schoolId;
        private String schoolName; // 필요하면 추가

        public static MajorSimpleRes fromEntity(Major major) {
            MajorSimpleRes dto = new MajorSimpleRes();
            dto.setId(major.getId());
            dto.setMajorName(major.getMajorName());

            if (major.getSchool() != null) {
                dto.setSchoolId(major.getSchool().getId());
                dto.setSchoolName(major.getSchool().getSchoolName());
            }
            return dto;
        }
    }
}
