package Umc.replendar.friend.dto.resDto;

import Umc.replendar.friend.entity.Buddy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class FriendRes {

    @Data
    @AllArgsConstructor
    @Builder
    public static class FriendListRes {
        private Long friendshipId; // 친구 관계 ID
        private Long friendId; // 친구 ID
        private String nickname;     // 친구 닉네임
        private String name;         // 친구 이름
        private int ongoingAssignments; // 진행 중인 과제 개수
        private Buddy buddyStatus;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class FriendSearchRes {
        private Long friendId;       // 친구 요청을 받을 사용자 ID
        private String nickname;      // 닉네임
        private String name;          // 이름
        private String statusMessage; // 상태 메시지
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class FriendGroupListRes {
        private Long groupId;
        private String groupName;
        private List<FriendListRes> friends;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class FriendNoteRes {
        private String note;
    }

}
