package Umc.replendar.activitylog.repository;

import Umc.replendar.activitylog.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityLog, Long> {
}
