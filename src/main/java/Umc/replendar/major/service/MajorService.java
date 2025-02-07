package Umc.replendar.major.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.user.dto.req.MajorDtoReq;
import Umc.replendar.user.dto.res.MajorDtoRes;

import java.util.List;

public interface MajorService {
    List<MajorDtoRes.MajorSimpleRes> searchMajor(Long schoolId, String keyword);

    MajorDtoRes.MajorSimpleRes createMajor(MajorDtoReq.CreateMajorReq request);

    ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLectureAssignment(long id, Long academicYear);
}
