package Umc.replendar.friend.converter;

import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.entity.Friend;
import Umc.replendar.user.entity.User;

public class FriToDto {
    public static FriendRes.FriendListRes toFriendListRes(Friend friend, Long userId, int ongoingAssignments) {
        return FriendRes.FriendListRes.builder()
                .friendId(friend.getFriendForUser(userId).getId())  // 친구의 ID
                .nickname(friend.getFriendForUser(userId).getNickname())  // 친구의 닉네임
                .name(friend.getFriendForUser(userId).getName())  // 친구의 이름
                .ongoingAssignments(ongoingAssignments)  // 진행 중인 과제 수
                .buddyStatus(friend.getBuddyStatusForUser(userId))  // 해당 사용자의 Buddy 상태
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
}
