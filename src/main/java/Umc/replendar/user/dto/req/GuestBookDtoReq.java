package Umc.replendar.user.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookDtoReq {
    private String nickname;
    private String name;
    private String teamName;
    private String teamPart;
    private String content;
}
