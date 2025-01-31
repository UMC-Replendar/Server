package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.FriendRequest;
import Umc.replendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    // sender와 receiver 간 요청이 이미 존재하는지 확인
    @Query("SELECT fr FROM FriendRequest fr WHERE fr.sender = :sender AND fr.receiver = :receiver")
    Optional<FriendRequest> findBySenderAndReceiver(@Param("sender") User sender, @Param("receiver") User receiver);

    // 특정 사용자에게 온 친구 요청 조회
    List<FriendRequest> findAllByReceiver(User receiver);

    // 특정 사용자가 보낸 친구 요청 조회
    List<FriendRequest> findAllBySender(User sender);


    List<FriendRequest> findAllByReceiverOrderByCreatedAtDesc(User user);
}