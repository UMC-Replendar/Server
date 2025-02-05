package Umc.replendar.user.repository;


import Umc.replendar.user.entity.Major;
import Umc.replendar.user.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
