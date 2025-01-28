package Umc.replendar.user.repository;

import Umc.replendar.user.entity.School;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
