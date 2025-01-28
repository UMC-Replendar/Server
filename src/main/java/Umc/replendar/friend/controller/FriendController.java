package Umc.replendar.friend.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.service.FriendService;
import Umc.replendar.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @Operation(summary = "친구 요청 보내기 API", description = "친구 요청을 보냅니다.")
    @PostMapping("/request")
    public ApiResponse<Long> sendFriendRequest(@RequestBody FriendReq.FriendRequestDto reqDto) {
        return friendService.sendFriendRequest(reqDto);
    }

    @Operation(summary = "친구 요청 응답 API", description = "친구 요청을 수락하거나 거절합니다.")
    @PostMapping("/request/respond")
    public ApiResponse<String> respondToFriendRequest(@RequestBody FriendReq.FriendAcceptDto reqDto) {
        if (reqDto.getIsAccepted()) {
            return friendService.acceptFriendRequest(reqDto.getRequestId());
        } else {
            return friendService.rejectFriendRequest(reqDto.getRequestId());
        }
    }
    @Operation(summary = "친구 목록 조회 API", description = "친구 목록과 진행 중인 과제 개수를 조회합니다.")
    @GetMapping("")
    public ApiResponse<List<FriendRes.FriendListRes>> getFriends(@RequestParam Long userId) {
        return friendService.getFriends(userId);
    }
    @Operation(summary = "TOP 5 친구 조회 API", description = "TOP 5 친구 목록을 조회합니다.")
    @GetMapping("/{userId}/top5")
    public ApiResponse<List<FriendRes.FriendListRes>> getTop5Friends(@PathVariable Long userId) {
        return friendService.getTop5Friends(userId);
    }

    @Operation(summary = "등록할 친구 검색 API", description = "닉네임으로 등록 가능한 친구를 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<FriendRes.FriendSearchRes> searchFriend(@RequestParam String nickname, @RequestParam Long userId) {
        return friendService.searchUserByNickname(nickname, userId);
    }
}

