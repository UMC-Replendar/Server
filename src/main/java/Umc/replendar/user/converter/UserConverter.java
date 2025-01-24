package Umc.replendar.user.converter;


import Umc.replendar.user.dto.res.UserDtoRes;
import Umc.replendar.user.entity.User;

public class UserConverter {

    public static UserDtoRes.UserLoginRes signInRes(User user, String accessToken, String nickName) {
        return UserDtoRes.UserLoginRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .accessToken(accessToken)
                .nickName(nickName)
                .build();
    }

}
