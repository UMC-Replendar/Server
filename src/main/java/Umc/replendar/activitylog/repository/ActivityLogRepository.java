package Umc.replendar.activitylog.repository;

import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findAllByUserOrderByCreatedAtDesc(User user);
}
