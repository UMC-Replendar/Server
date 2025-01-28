package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.dto.resDto.FriendRes.FriendListRes;

import java.util.List;

public interface FriendService {

    ApiResponse<List<FriendListRes>> getFriends(Long userId); //친구 목록 조회
    ApiResponse<List<FriendRes.FriendListRes>> getTop5Friends(Long userId); //친구 목록 조회(top5)
    ApiResponse<FriendRes.FriendSearchRes> searchUserByNickname(String nickname, Long userId);
    ApiResponse<Long> sendFriendRequest(FriendReq.FriendRequestDto reqDto);
    ApiResponse<String> acceptFriendRequest(Long requestId);
    ApiResponse<String> rejectFriendRequest(Long requestId);
    ApiResponse<String> updateBestFriend(FriendReq.FriendBuddyReqDto reqDto);

}
