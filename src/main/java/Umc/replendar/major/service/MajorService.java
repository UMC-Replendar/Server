package Umc.replendar.major.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.major.dto.req.LectureReq;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.user.dto.req.MajorDtoReq;
import Umc.replendar.user.dto.res.MajorDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MajorService {
    List<MajorDtoRes.MajorSimpleRes> searchMajor(Long schoolId, String keyword);

    MajorDtoRes.MajorSimpleRes createMajor(MajorDtoReq.CreateMajorReq request);

    ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLectureAssignment(long id, Long academicYear);

    ApiResponse<LectureAssignmentRes.LectureAssignmentPostRes> getLectureCreateData(long userId, Long lectureAssignmentId);

    ApiResponse<List<LectureAssignmentRes.LecturesRes>> getLectures(long userId, Long academicYear);

    ApiResponse<Page<LectureAssignmentRes.LectureNewsRes>> getLectureNews(long userId, Pageable pageable);

    ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLectureAssignmentSort(long id, String type, String sort, Long academicYear, Long majorId);

    ApiResponse<String> createLectureAssignment(long userId, LectureReq.LectureAssignmentPostReq request);
}
