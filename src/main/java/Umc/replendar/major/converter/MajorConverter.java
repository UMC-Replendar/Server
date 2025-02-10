package Umc.replendar.major.converter;

import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.major.entity.Lecture;
import Umc.replendar.major.entity.LectureAssignment;
import Umc.replendar.major.entity.UserLectureAssignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MajorConverter {
    public static LectureAssignmentRes.LectureAssignmentGetRes toLectureAssignmentGetRes(LectureAssignment lectureAssignment, boolean check) {
        return LectureAssignmentRes.LectureAssignmentGetRes.builder()
                .LectureAssignmentId(lectureAssignment.getId())
                .title(lectureAssignment.getTitle())
                .professor(lectureAssignment.getLecture().getProfessor())
                .lectureName(lectureAssignment.getLecture().getLectureName())
                .academicYear(lectureAssignment.getLecture().getAcademicYear())
                .created_date(lectureAssignment.getCreatedAt().format(DateTimeFormatter.ofPattern("MM/dd")))
                .due_date(lectureAssignment.getDueDate().toString())
                .check(check ? Check.CHECK : Check.UNCHECK)
                .build();

    }

    public static LectureAssignmentRes.LectureAssignmentPostRes toLectureAssignmentPostRes(LectureAssignment lectureAssignment) {
        return LectureAssignmentRes.LectureAssignmentPostRes.builder()
                .LectureAssignmentId(lectureAssignment.getId())
                .title(lectureAssignment.getTitle())
                .professor(lectureAssignment.getLecture().getProfessor())
                .lectureName(lectureAssignment.getLecture().getLectureName())
                .academicYear(lectureAssignment.getLecture().getAcademicYear())
                .due_date(lectureAssignment.getDueDate().toString())
                .due_time("23:55")
                .content(lectureAssignment.getContent())
                .build();
    }

    public static LectureAssignmentRes.LecturesRes toLecturesRes(Lecture lecture) {
        return LectureAssignmentRes.LecturesRes.builder()
                .LectureId(lecture.getId())
                .lectureName(lecture.getLectureName())
                .professor(lecture.getProfessor())
                .academicYear(lecture.getAcademicYear())
                .build();
    }

    public static LectureAssignmentRes.LectureNewsRes toLectureNewsRes(UserLectureAssignment userLectureAssignment, List<Long> lectureAssignmentIds) {
        Check check;
        if (lectureAssignmentIds.contains(userLectureAssignment.getLectureAssignment().getId())) {
            check = Check.CHECK;
        } else {
            check = Check.UNCHECK;
        }

        String time = formatTimeAgo(userLectureAssignment.getCreatedAt());

        return LectureAssignmentRes.LectureNewsRes.builder()
                .friendId(userLectureAssignment.getUser().getId())
                .assignmentId(userLectureAssignment.getAssignment().getId())
                .lectureAssignmentId(userLectureAssignment.getLectureAssignment().getId())
                .time(time)
                .nickname(userLectureAssignment.getUser().getNickname())
                .title(userLectureAssignment.getLectureAssignment().getTitle())
                .check(check)
                .build();
    }

    // Helper 메서드: "1시간 전", "2일 전" 등으로 변환
    public static String formatTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(createdAt, now);
        long hours = ChronoUnit.HOURS.between(createdAt, now);
        long days = ChronoUnit.DAYS.between(createdAt, now);

        if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else {
            return days + "일 전";
        }
    }

}
