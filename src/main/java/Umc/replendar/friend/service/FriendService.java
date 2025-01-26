package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.user.entity.User;

import java.util.List;

public interface FriendService {
    ApiResponse<String> addFriend(FriendReq reqDto); // 친구 등록
    ApiResponse<List<FriendRes.FriendListRes>> getFriends(Long userId); //친구 목록 조회
    public ApiResponse<FriendRes.FriendSearchRes> searchUserByNickname(String nickname, Long userId);

//    ApiResponse<FriendRes> getFriendDetail(Long friendId); // 친구 상세 정보 조회
//    ApiResponse<String> deleteFriend(Long friendId); // 친구 삭제
}
