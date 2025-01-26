package Umc.replendar.friend.converter;

import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.user.entity.User;

public class FriToDto {
    public static FriendRes.FriendListRes toFriendListRes(User friend, int ongoingAssignments) {
        return FriendRes.FriendListRes.builder()
                .friendId(friend.getId())
                .nickname(friend.getNickname())
                .name(friend.getName())
                .ongoingAssignments(ongoingAssignments)
                .build();
    }
    public static FriendRes.FriendSearchRes toFriendSearchRes(User user) {
        return FriendRes.FriendSearchRes.builder()
                .nickname(user.getNickname())
                .name(user.getName())
                .statusMessage(user.getStatusMessage())
                .build();
    }
}
