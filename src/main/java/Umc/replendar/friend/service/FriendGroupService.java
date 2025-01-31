package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;

import java.util.List;

public interface FriendGroupService {
    ApiResponse<String> createFriendGroup(Long userId, FriendReq.CreateGroupDto reqDto);
    ApiResponse<String> deleteFriendGroup(Long userId, Long groupId);
    ApiResponse<String> addFriendToGroup(Long userId, Long groupId, FriendReq.AddFriendToGroupDto reqDto);
    ApiResponse<String> removeFriendFromGroup(Long userId, Long groupId, Long friendshipId);
    ApiResponse<List<FriendRes.FriendGroupListRes>> getFriendGroups(Long userId);
    ApiResponse<List<FriendRes.FriendListRes>> getFriendsNotInGroup(Long userId, Long groupId);
}
