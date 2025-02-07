package Umc.replendar.major.dto.res;

import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.user.entity.AcademicYear;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LectureAssignmentRes {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LectureAssignmentGetRes {
        private Long LectureAssignmentId;
        private String title;
        private String professor;
        private String lectureName;
        private AcademicYear academicYear;
        private String created_date;
        private String due_date;
        private Check check;
    }

}
