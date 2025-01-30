package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.FriendGroup;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendGroupRepository extends JpaRepository<FriendGroup, Long> {
    // 특정 사용자의 모든 친구 그룹 조회
    List<FriendGroup> findAllByUserId(Long userId);

    // 특정 사용자와 그룹 ID로 그룹 조회
    Optional<FriendGroup> findByIdAndUserId(Long groupId, Long userId);
}
