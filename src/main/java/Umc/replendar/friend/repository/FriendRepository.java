package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.Friend;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    // 특정 사용자의 모든 친구 관계 조회
    List<Friend> findAllByUserIdOrFriendId(Long userId, Long friendId);

    // 특정 사용자와 친구가 이미 친구인지 확인
    boolean existsByUserAndFriend(User user, User friend);
}
