package Umc.replendar.friend.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.service.FriendGroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend-groups")
@RequiredArgsConstructor
public class FriendGroupController {

    private final FriendGroupService friendGroupService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "친구 그룹 생성 API", description = "새로운 친구 그룹을 생성합니다.")
    @PostMapping("")
    public ApiResponse<String> createFriendGroup(@RequestBody FriendReq.CreateGroupDto reqDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendGroupService.createFriendGroup(userId, reqDto);
    }

    @Operation(summary = "친구 그룹 삭제 API", description = "친구 그룹을 삭제합니다.")
    @DeleteMapping("/{groupId}")
    public ApiResponse<String> deleteFriendGroup(@PathVariable Long groupId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendGroupService.deleteFriendGroup(userId, groupId);
    }

    @Operation(summary = "친구 그룹에 친구 추가 API", description = "친구 그룹에 특정 친구를 추가합니다.")
    @PostMapping("/{groupId}/add-multiple")
    public ApiResponse<String> addFriendToGroup(@PathVariable Long groupId, @RequestBody FriendReq.AddFriendToGroupDto reqDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendGroupService.addFriendToGroup(userId, groupId, reqDto);
    }

    @Operation(summary = "친구 그룹에서 친구 제거 API", description = "친구 그룹에서 특정 친구를 제거합니다.")
    @DeleteMapping("/{groupId}/remove/{friendshipId}")
    public ApiResponse<String> removeFriendFromGroup(@PathVariable Long groupId, @PathVariable Long friendshipId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendGroupService.removeFriendFromGroup(userId, groupId, friendshipId);
    }

    @Operation(summary = "친구 그룹 목록 조회 API", description = "사용자가 만든 친구 그룹을 조회합니다.")
    @GetMapping("")
    public ApiResponse<List<FriendRes.FriendGroupListRes>> getFriendGroups() {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendGroupService.getFriendGroups(userId);
    }
    @Operation(summary = "그룹에 추가할 친구 목록 조회 API", description = "그룹에 추가 가능한 친구 목록을 조회 합니다.")
    @GetMapping("/{groupId}/available-friends")
    public ApiResponse<List<FriendRes.FriendListRes>> getFriendsNotInGroup(@PathVariable Long groupId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendGroupService.getFriendsNotInGroup(userId, groupId);
    }
}

