package Umc.replendar.assignment.repository;

import Umc.replendar.assignment.entity.AssNotifyCycle;
import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.assignment.entity.GeneralSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface AssNotifyCycleRepository extends JpaRepository<AssNotifyCycle, Long> {
    List<AssNotifyCycle> findAllByAssignment(Assignment assignment);

    void deleteAllByAssignment(Assignment assignment);

    List<AssNotifyCycle> findAllByScheduledAtBefore(LocalDateTime now);

    List<AssNotifyCycle> findAllByScheduledAtBeforeAndNotifyCheck(LocalDateTime now, GeneralSettings generalSettings);
}
