package Umc.replendar.major.service;

import Umc.replendar.major.converter.MajorConverter;
import Umc.replendar.major.dto.res.LectureAssignmentRes;
import Umc.replendar.major.entity.LectureAssignment;
import Umc.replendar.major.repository.LectureAssignmentRepository;
import Umc.replendar.major.repository.UserLectureAssignmentRepository;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureAssignmentServiceImpl implements LectureAssignmentService {

    private final LectureAssignmentRepository lectureAssignmentRepository;
    private final UserLectureAssignmentRepository userLectureAssignmentRepository;
    private final UserRepository userRepository;

    @Override
    public List<LectureAssignmentRes.LectureAssignmentGetRes> getAssignmentsByLectureId(Long userId, Long lectureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID입니다."));

        // 특정 강의 ID에 해당하는 모든 과제 가져오기
        List<LectureAssignment> lectureAssignments = lectureAssignmentRepository.findAllByLectureIdOrderByCreatedAtDesc(lectureId);

        // 사용자의 과제 등록 여부를 바로 확인하여 변환
        return lectureAssignments.stream()
                .map(lectureAssignment -> {
                    boolean isRegistered = userLectureAssignmentRepository.existsByUserAndLectureAssignment(user, lectureAssignment);
                    return MajorConverter.toLectureAssignmentGetRes(lectureAssignment, isRegistered);
                })
                .toList();
    }
}

