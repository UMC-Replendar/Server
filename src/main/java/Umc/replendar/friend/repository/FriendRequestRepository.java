package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.FriendRequest;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    // sender와 receiver 간 요청이 이미 존재하는지 확인
    boolean existsBySenderAndReceiver(User sender, User receiver);

    // 특정 사용자에게 온 친구 요청 조회
    List<FriendRequest> findAllByReceiver(User receiver);

    // 특정 사용자가 보낸 친구 요청 조회
    List<FriendRequest> findAllBySender(User sender);
}