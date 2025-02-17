package Umc.replendar.user.converter;

import Umc.replendar.user.dto.req.GuestBookDtoReq;
import Umc.replendar.user.dto.res.GuestBookDtoRes;
import Umc.replendar.user.entity.GuestBook;
import Umc.replendar.user.entity.User;

public class GuestBookConverter {

    public static GuestBook toEntity(User user, GuestBookDtoReq dto) {
        return GuestBook.builder()
                .user(user)
                .nickname(dto.getNickname())
                .name(dto.getName())
                .teamName(dto.getTeamName())
                .teamPart(dto.getTeamPart())
                .content(dto.getContent())
                .build();
    }

    public static GuestBookDtoRes toResDto(GuestBook entity) {
        return GuestBookDtoRes.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .name(entity.getName())
                .teamName(entity.getTeamName())
                .teamPart(entity.getTeamPart())
                .content(entity.getContent())
                .build();
    }
}
