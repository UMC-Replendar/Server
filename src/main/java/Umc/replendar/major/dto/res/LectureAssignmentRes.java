package Umc.replendar.major.dto.res;

import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.user.entity.AcademicYear;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LectureAssignmentPostRes {
        private Long LectureAssignmentId;
        private String title;
        private String lectureName;
        private String professor;
        private AcademicYear academicYear;
        private String due_date;
        private String due_time;
        private String content;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LecturesRes {
        private Long LectureId;
        private String lectureName;
        private String professor;
        private AcademicYear academicYear;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LectureNewsRes {
        private Long friendId;
        private Long assignmentId;
        private Long lectureAssignmentId;
        private String time;
        private String nickname;
        private String title;
        private Check check;
    }

}
