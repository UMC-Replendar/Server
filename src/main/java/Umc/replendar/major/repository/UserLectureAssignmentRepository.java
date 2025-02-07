package Umc.replendar.major.repository;

import Umc.replendar.major.entity.LectureAssignment;
import Umc.replendar.major.entity.UserLectureAssignment;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLectureAssignmentRepository extends JpaRepository<UserLectureAssignment, Long> {
    boolean existsByUserAndLectureAssignment(User user, LectureAssignment lectureAssignment);

}
