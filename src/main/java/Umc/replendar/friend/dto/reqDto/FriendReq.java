package Umc.replendar.friend.dto.reqDto;

import Umc.replendar.friend.entity.Buddy;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class FriendReq {

     // 친구 요청을 생성할 때 사용하는 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendRequestDto  {

        @Schema(hidden = true) // Swagger 문서에서만 userId를 숨김
        private Long userId; // 요청을 보낸 사용자 ID
        private Long friendId; // 친구 요청을 받을 사용자 ID
    }


     // 친구 요청을 수락할 때 사용하는 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendAcceptDto  {
        private Long requestId; // 친구 요청 ID
        private Boolean isAccepted; // 수락 여부 (true: 수락, false: 거절)
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendBuddyReqDto {
        @Schema(hidden = true)
        private Long userId;    // 로그인한 사용자 ID
        private Long friendId;  // 친한 친구로 설정할 대상 ID
        private Buddy buddyStatus; // 설정할 친한 친구 상태 (YES / NO)
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateGroupDto {
        private String groupName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddFriendToGroupDto {
        private List<Long> friendshipIds;  // 여러 명의 친구 관계 ID 리스트
    }

}