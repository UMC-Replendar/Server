package Umc.replendar.assignment.repository;

import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.assignment.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    List<Share> findAllByAssignment(Assignment assignment);
}
