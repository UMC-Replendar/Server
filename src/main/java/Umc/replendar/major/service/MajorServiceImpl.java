package Umc.replendar.major.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.major.converter.MajorConverter;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.major.entity.*;
import Umc.replendar.major.repository.LectureAssignmentRepository;
import Umc.replendar.major.repository.LectureRepository;
import Umc.replendar.major.repository.MajorRepository;
import Umc.replendar.major.repository.SchoolRepository;
import Umc.replendar.major.repository.UserLectureAssignmentRepository;
import Umc.replendar.user.dto.req.MajorDtoReq;
import Umc.replendar.user.dto.res.MajorDtoRes;
import Umc.replendar.user.entity.AcademicYear;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MajorServiceImpl implements MajorService {
    private final MajorRepository majorRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final LectureAssignmentRepository lectureAssignmentRepository;
    private final UserLectureAssignmentRepository userLectureAssignmentRepository;

    public List<MajorDtoRes.MajorSimpleRes> searchMajor(Long schoolId, String keyword) {

        List<Major> majorList;
        if (keyword == null || keyword.trim().isEmpty()) {
            majorList = majorRepository.findBySchoolId(schoolId);
        } else {
            majorList = majorRepository.findBySchoolIdAndMajorNameContaining(schoolId, keyword);
        }

        return majorList.stream()
                .map(MajorDtoRes.MajorSimpleRes::fromEntity)
                .toList();
    }

    public MajorDtoRes.MajorSimpleRes createMajor(MajorDtoReq.CreateMajorReq request) {
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학교 ID입니다."));

        if (majorRepository.existsBySchoolIdAndMajorName(
                request.getSchoolId(), request.getMajorName())) {
            throw new IllegalArgumentException("이미 해당 학교에 존재하는 학과명입니다.");
        }

        Major major = new Major();
        major.setSchool(school);
        major.setMajorName(request.getMajorName());

        Major saved = majorRepository.save(major);
        return MajorDtoRes.MajorSimpleRes.fromEntity(saved);
    }

    @Override
    public ApiResponse<List<LectureAssignmentRes.LectureAssignmentGetRes>> getLectureAssignment(long userId, Long academicYear) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID입니다."));

        //학년 설정
        AcademicYear year;
        if (academicYear == null) {
            year = user.getAcademicYear();
        }else{
            try {
                year = AcademicYear.valueOf("YEAR_" + academicYear);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("올바르지 않은 학년 값입니다: " + academicYear);
            }
        }

        // 강좌 조회
        if(user.getMajor() == null) {
            throw new IllegalArgumentException("사용자의 학과 정보가 존재하지 않습니다.");
        }

        List<Lecture> lectures = lectureRepository.findAllByMajorIdAndAcademicYear(user.getMajor().getId(), year);

        List<Long> lectureIds = lectures.stream()
                .map(Lecture::getId)
                .toList();

        List<LectureAssignment> lectureAssignments = lectureAssignmentRepository.findAllByLectureIdInOrderByCreatedAtDesc(lectureIds);
        return ApiResponse.onSuccess(lectureAssignments.stream().map(
                lectureAssignment -> MajorConverter.toLectureAssignmentGetRes(lectureAssignment, userLectureAssignmentRepository.existsByUserAndLectureAssignment(user, lectureAssignment)))
                .toList());
    }

    @Override
    public ApiResponse<LectureAssignmentRes.LectureAssignmentPostRes> getLectureCreateData(long userId, Long lectureAssignmentId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID입니다."));

        LectureAssignment lectureAssignment = lectureAssignmentRepository.findById(lectureAssignmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌 과제 ID입니다."));

        return ApiResponse.onSuccess(MajorConverter.toLectureAssignmentPostRes(lectureAssignment));

    }


}
