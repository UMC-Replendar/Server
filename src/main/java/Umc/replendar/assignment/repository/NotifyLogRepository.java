package Umc.replendar.assignment.repository;

import Umc.replendar.assignment.entity.NotifyLog;
import Umc.replendar.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyLogRepository extends JpaRepository<NotifyLog, Long> {


    Page<NotifyLog> findAllByUserOrderByCreatedAtDesc(User user, Pageable adjustedPageable);
}
