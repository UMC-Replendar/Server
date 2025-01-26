package Umc.replendar.friend.dto.resDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRes {
    private boolean isSuccess;
    private String code;
    private String message;
    private String result;
}