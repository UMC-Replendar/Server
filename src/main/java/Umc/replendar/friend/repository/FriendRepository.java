package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.Friend;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByUserIdOrFriendId(Long userId, Long friendId);
}
