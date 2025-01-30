package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.friend.converter.FriToDto;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.entity.friendship;
import Umc.replendar.friend.entity.FriendGroup;
import Umc.replendar.friend.entity.FriendGroupMember;
import Umc.replendar.friend.repository.FriendGroupMemberRepository;
import Umc.replendar.friend.repository.FriendGroupRepository;
import Umc.replendar.friend.repository.FriendRepository;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendGroupServiceImpl implements FriendGroupService {

    private final FriendGroupRepository friendGroupRepository;
    private final FriendGroupMemberRepository friendGroupMemberRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    // 친구 그룹 생성
    @Override
    public ApiResponse<String> createFriendGroup(Long userId, FriendReq.CreateGroupDto reqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 해당 사용자가 만든 기존 그룹 중 같은 이름이 있는지 확인
        boolean isDuplicate = friendGroupRepository.findAllByUserId(userId).stream()
                .anyMatch(group -> group.getName().equals(reqDto.getGroupName()));

        if (isDuplicate) {
            return ApiResponse.onFailure("DUPLICATE_GROUP_NAME", "이미 존재하는 그룹 이름입니다.", null);
        }

        FriendGroup friendGroup = FriendGroup.builder()
                .name(reqDto.getGroupName())
                .user(user)
                .build();
        friendGroupRepository.save(friendGroup);

        return ApiResponse.onSuccess("친구 그룹이 생성되었습니다.");
    }

    // 친구 그룹 삭제
    @Override
    public ApiResponse<String> deleteFriendGroup(Long userId, Long groupId) {
        FriendGroup friendGroup = friendGroupRepository.findByIdAndUserId(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("친구 그룹이 존재하지 않거나 권한이 없습니다."));

        friendGroupRepository.delete(friendGroup);
        return ApiResponse.onSuccess("친구 그룹이 삭제되었습니다.");
    }

    // 친구 그룹에 친구 추가
    @Override
    public ApiResponse<String> addFriendToGroup(Long userId, Long groupId, FriendReq.AddFriendToGroupDto reqDto) {
        FriendGroup friendGroup = friendGroupRepository.findByIdAndUserId(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("친구 그룹이 존재하지 않거나 권한이 없습니다."));

        for (Long friendshipId : reqDto.getFriendshipIds()) {
            friendship friendship = friendRepository.findById(friendshipId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 친구 관계가 존재하지 않습니다."));

            boolean isAlreadyInGroup = friendGroupMemberRepository.existsByFriendshipAndGroup(friendship, friendGroup);
            if (isAlreadyInGroup) {
                return ApiResponse.onFailure("FRIEND_ALREADY_IN_GROUP", "해당 친구는 이미 그룹에 속해 있습니다.", null);
            }

            FriendGroupMember member = FriendGroupMember.builder()
                    .group(friendGroup)
                    .friendship(friendship)
                    .build();
            friendGroupMemberRepository.save(member);
        }
        return ApiResponse.onSuccess("친구가 그룹에 추가되었습니다.");
    }

    // 친구 그룹에서 친구 제거
    @Override
    public ApiResponse<String> removeFriendFromGroup(Long userId, Long groupId, Long friendshipId) {

        FriendGroup friendGroup = friendGroupRepository.findByIdAndUserId(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("친구 그룹이 존재하지 않거나 권한이 없습니다."));

        friendship friendship = friendRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구 관계가 존재하지 않습니다."));

        friendGroupMemberRepository.findByFriendshipAndGroup(friendship, friendGroup)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구는 이 그룹에 속해 있지 않습니다."));

        friendGroupMemberRepository.deleteByFriendshipIdAndGroupId(friendshipId, groupId);
        return ApiResponse.onSuccess("친구가 그룹에서 제거되었습니다.");
    }

    // 친구 그룹 목록 조회
    @Override
    public ApiResponse<List<FriendRes.FriendGroupListRes>> getFriendGroups(Long userId) {
        List<FriendGroup> friendGroups = friendGroupRepository.findAllByUserId(userId);
        List<FriendRes.FriendGroupListRes> response = FriToDto.toFriendGroupListRes(friendGroups, userId, assignmentRepository);
        return ApiResponse.onSuccess(response);
    }
    @Override
    public ApiResponse<List<FriendRes.FriendListRes>> getFriendsNotInGroup(Long userId, Long groupId) {
        // 현재 사용자의 모든 친구 조회
        List<friendship> allFriends = friendRepository.findAllByUserIdOrFriendId(userId, userId);

        // 해당 그룹에 속한 친구들 조회
        List<friendship> friendsInGroup = friendGroupMemberRepository.findAllByGroupId(groupId).stream()
                .map(FriendGroupMember::getFriendship)
                .collect(Collectors.toList());

        // 그룹에 속하지 않은 친구들 필터링
        List<FriendRes.FriendListRes> availableFriends = allFriends.stream()
                .filter(friend -> !friendsInGroup.contains(friend)) // 그룹에 없는 친구만 포함
                .map(friend -> FriToDto.toFriendListRes(friend, userId, 0)) // 과제 개수는 0으로 설정
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(availableFriends);
    }
}
