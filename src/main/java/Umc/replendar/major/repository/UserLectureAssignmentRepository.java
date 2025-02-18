package Umc.replendar.major.repository;

import Umc.replendar.major.entity.LectureAssignment;
import Umc.replendar.major.entity.UserLectureAssignment;
import Umc.replendar.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserLectureAssignmentRepository extends JpaRepository<UserLectureAssignment, Long> {
    boolean existsByUserAndLectureAssignment(User user, LectureAssignment lectureAssignment);

    Page<UserLectureAssignment> findAllByLectureAssignmentIdInAndUserIdNotOrderByCreatedAtDesc(List<Long> lectureAssignmentIds, Long userId, Pageable adjustedPageable);

    List<UserLectureAssignment> findAllByUserId(Long id);

    UserLectureAssignment findByAssignmentId(Long id);
}
