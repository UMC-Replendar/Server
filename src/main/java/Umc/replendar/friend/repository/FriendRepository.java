package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.Friend;
import Umc.replendar.friend.entity.FriendRequest;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    // 특정 사용자의 모든 친구 관계 조회(둘다 같은 userId로 조회)
    List<Friend> findAllByUserIdOrFriendId(Long userId, Long friendId);
    // 특정 사용자와 특정 친구 관계 조회(userId,friendId - 순서 상관 없이 조회 가능)
    @Query("SELECT f FROM Friend f WHERE (f.user.id = :userId AND f.friend.id = :friendId) OR (f.user.id = :friendId AND f.friend.id = :userId)")
    Optional<Friend> findFriendByUserAndFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    // 특정 사용자와 친구가 이미 친구인지 확인
    boolean existsByUserAndFriend(User user, User friend);



}
