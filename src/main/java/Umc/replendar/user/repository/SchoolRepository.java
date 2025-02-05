package Umc.replendar.user.repository;

import Umc.replendar.user.entity.School;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    // 학교 이름으로 부분 검색
    List<School> findBySchoolNameContaining(String keyword);

    // 이름 중복 체크
    boolean existsBySchoolName(String schoolName);

}
