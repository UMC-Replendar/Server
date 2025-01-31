package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.dto.resDto.FriendRes.FriendListRes;

import java.util.List;

public interface FriendService {

    ApiResponse<List<FriendListRes>> getFriends(Long userId); //친구 목록 조회(전체)
    ApiResponse<List<FriendRes.FriendListRes>> getTopFriends(Long userId,int limit); //친구 목록 조회(개수 조절)
    ApiResponse<FriendRes.FriendSearchRes> searchUserByNickname(String nickname, Long userId);
    ApiResponse<Long> sendFriendRequest(FriendReq.FriendRequestDto reqDto);
    ApiResponse<String> acceptFriendRequest(Long requestId);
    ApiResponse<String> rejectFriendRequest(Long requestId);
    ApiResponse<String> updateBestFriend(FriendReq.FriendBuddyReqDto reqDto);
    public ApiResponse<String> deleteFriend(Long userId, Long friendId);
    ApiResponse<String> updateFriendNote(Long userId, FriendReq.FriendNoteReqDto reqDto);
    ApiResponse<FriendRes.FriendNoteRes> getFriendNote(Long userId, Long friendId);

}
