package Umc.replendar.assignment.repository;

import Umc.replendar.assignment.entity.AssNotifyCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssNotifyCycleRepository extends JpaRepository<AssNotifyCycle, Long> {
}
