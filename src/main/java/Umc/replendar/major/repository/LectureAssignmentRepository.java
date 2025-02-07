package Umc.replendar.major.repository;

import Umc.replendar.major.entity.LectureAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureAssignmentRepository extends JpaRepository<LectureAssignment, Long> {



}
