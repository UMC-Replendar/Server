package Umc.replendar.friend.converter;

import Umc.replendar.assignment.entity.GeneralSettings;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.entity.Buddy;
import Umc.replendar.friend.entity.friendship;
import Umc.replendar.friend.entity.FriendGroup;
import Umc.replendar.user.entity.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FriToDto {
    public static FriendRes.FriendListRes toFriendListRes(friendship friendship, Long userId, int ongoingAssignments) {
        return FriendRes.FriendListRes.builder()
                .friendshipId(friendship.getId())  // 친구관계 ID
                .friendId(friendship.getFriendForUser(userId).getId())  // 친구 ID
                .nickname(friendship.getFriendForUser(userId).getNickname())  // 친구의 닉네임
                .name(friendship.getFriendForUser(userId).getName())  // 친구의 이름
                .ongoingAssignments(ongoingAssignments)  // 진행 중인 과제 수
                .buddyStatus(friendship.getBuddyStatusForUser(userId))  // 해당 사용자의 Buddy 상태
                .build();
    }
    public static FriendRes.FriendSearchRes toFriendSearchRes(User user) {
        return FriendRes.FriendSearchRes.builder()
                .friendId(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .statusMessage(user.getStatusMessage())
                .build();
    }

    public static List<FriendRes.FriendGroupListRes> toFriendGroupListRes(
            List<FriendGroup> friendGroups, Long userId, AssignmentRepository assignmentRepository) {

        return friendGroups.stream()
                .map(group -> FriendRes.FriendGroupListRes.builder()
                        .groupId(group.getId())
                        .groupName(group.getName())
                        .friends(group.getMembers().stream()
                                .map(member -> {
                                    friendship friendship = member.getFriendship();
                                    int ongoingAssignments = assignmentRepository.countByUserAndStatusAndVisibility(
                                            friendship.getFriendForUser(userId),
                                            Status.ONGOING,
                                            GeneralSettings.ON
                                    );
                                    return toFriendListRes(friendship, userId, ongoingAssignments);
                                })
                                .sorted(Comparator.comparing(
                                                (FriendRes.FriendListRes f) -> f.getBuddyStatus() == Buddy.YES ? 0 : 1)
                                        .thenComparing(FriendRes.FriendListRes::getNickname))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
