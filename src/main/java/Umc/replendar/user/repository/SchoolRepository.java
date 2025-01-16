package Umc.replendar.user.repository;

import Umc.replendar.user.entity.School;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
