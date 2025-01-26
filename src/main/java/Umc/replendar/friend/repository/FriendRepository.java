package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.Friend;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    // 특정 사용자의 모든 친구 관계 조회
    List<Friend> findAllByUserIdOrFriendId(Long userId, Long friendId);

    // 특정 사용자와 친구가 이미 친구인지 확인
    boolean existsByUserAndFriend(User user, User friend);

    @Query("SELECT f.friend FROM Friend f WHERE f.user.id = :userId AND f.friend.id != :userId " +
            "UNION " +
            "SELECT f.user FROM Friend f WHERE f.friend.id = :userId AND f.user.id != :userId")
    List<User> findFriendsByUserId(@Param("userId") Long userId);

}
