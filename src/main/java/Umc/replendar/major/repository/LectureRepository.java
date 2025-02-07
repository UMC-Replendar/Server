package Umc.replendar.major.repository;

import Umc.replendar.major.entity.Lecture;
import Umc.replendar.major.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {



}
