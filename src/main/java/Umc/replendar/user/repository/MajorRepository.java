package Umc.replendar.user.repository;


import Umc.replendar.user.entity.Major;
import Umc.replendar.user.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    // 특정 학교 내 학과 목록
    List<Major> findBySchoolId(Long schoolId);

    // 특정 학교 내에서 이름 부분 검색
    List<Major> findBySchoolIdAndMajorNameContaining(Long schoolId, String keyword);

    // 중복 확인
    boolean existsBySchoolIdAndMajorName(Long schoolId, String majorName);
}
