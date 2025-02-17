package Umc.replendar.major.service;

import Umc.replendar.major.dto.res.LectureAssignmentRes;

import java.util.List;

public interface LectureAssignmentService {
    List<LectureAssignmentRes.LectureAssignmentGetRes> getAssignmentsByLectureId(Long userId, Long lectureId);
}

