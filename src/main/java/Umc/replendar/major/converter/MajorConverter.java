package Umc.replendar.major.converter;

import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.major.entity.LectureAssignment;

import java.time.format.DateTimeFormatter;

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
}
