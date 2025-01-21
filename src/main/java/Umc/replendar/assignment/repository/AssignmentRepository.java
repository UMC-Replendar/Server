package Umc.replendar.assignment.repository;

import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findTop10ByUserAndStatusOrderByDueDate(User user, Status status);

    List<Assignment> findAllByUserAndDueDateBetweenAndStatusOrderByDueDate(User user, LocalDateTime dueDate, LocalDateTime dueDate2, Status status);
}
