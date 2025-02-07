package Umc.replendar.major.repository;

import Umc.replendar.major.entity.LectureAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureAssignmentRepository extends JpaRepository<LectureAssignment, Long> {


    List<LectureAssignment> findAllByLectureIdOrderByCreatedAtDesc(Long id);

    List<LectureAssignment> findAllByLectureIdInOrderByCreatedAtDesc(List<Long> lectureIds);
}
