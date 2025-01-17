package Umc.replendar.assignment.repository;

import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findTop10ByUserAndStatusOrderByDueDate(User user, Status status);
}
