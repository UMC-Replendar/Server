package Umc.replendar.major.repository;

import Umc.replendar.major.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLectureAssignment extends JpaRepository<UserLectureAssignment, Long> {
}
