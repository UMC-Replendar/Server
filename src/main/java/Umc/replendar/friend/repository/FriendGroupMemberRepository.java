package Umc.replendar.friend.repository;

import Umc.replendar.friend.entity.friendship;
import Umc.replendar.friend.entity.FriendGroup;
import Umc.replendar.friend.entity.FriendGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendGroupMemberRepository extends JpaRepository<FriendGroupMember, Long> {
    // 그룹에서 특정 친구 삭제
    void deleteByFriendshipIdAndGroupId(Long friendshipId, Long groupId);
    // 특정 그룹에 특정 친구가 이미 속해 있는지 확인
    boolean existsByFriendshipAndGroup(friendship friendship, FriendGroup group);
    // 특정 그룹에서 특정 친구 멤버십 찾기
    Optional<FriendGroupMember> findByFriendshipAndGroup(friendship friendship, FriendGroup group);
    // 특정 그룹에 속한 모든 멤버 조회
    List<FriendGroupMember> findAllByGroupId(Long groupId);
}
