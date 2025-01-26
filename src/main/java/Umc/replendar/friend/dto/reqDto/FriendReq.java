package Umc.replendar.friend.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendReq {
    private Long userId;    // 요청을 보낸 사용자 ID
    private Long friendId;  // 친구로 추가할 사용자 ID
    private String userNote; // 추가 메모
}
