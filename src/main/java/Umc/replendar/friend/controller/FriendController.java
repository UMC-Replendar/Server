package Umc.replendar.friend.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "친구 요청 보내기 API", description = "친구 요청을 보냅니다.")
    @PostMapping("/request")
    public ApiResponse<Long> sendFriendRequest(@RequestBody FriendReq.FriendRequestDto reqDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        reqDto.setUserId(userId);
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
    public ApiResponse<List<FriendRes.FriendListRes>> getFriends(@RequestParam(required = false, defaultValue = "0") int limit) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return limit > 0 ? friendService.getTopFriends(userId, limit) : friendService.getFriends(userId);
    }

    @Operation(summary = "등록할 친구 검색 API", description = "닉네임으로 등록 가능한 친구를 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<FriendRes.FriendSearchRes> searchFriend(@RequestParam String nickname) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendService.searchUserByNickname(nickname, userId);
    }
    @Operation(summary = "친한 친구 설정 API", description = "친구를 친한 친구로 설정하거나 해제합니다.")
    @PatchMapping("/best-friend")
    public ApiResponse<String> setBestFriend(@RequestBody FriendReq.FriendBuddyReqDto reqDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        reqDto.setUserId(userId);
        return friendService.updateBestFriend(reqDto);
    }
    @Operation(summary = "친구 삭제 API", description = "친구 관계를 삭제합니다.")
    @DeleteMapping("")
    public ApiResponse<String> deleteFriend(@RequestParam Long friendId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendService.deleteFriend(userId, friendId);
    }
    @Operation(summary = "친구 메모 작성/수정 API", description = "친구에게 남길 메모를 작성하거나 수정합니다.")
    @PatchMapping("/note")
    public ApiResponse<String> updateFriendNote(@RequestBody FriendReq.FriendNoteReqDto reqDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendService.updateFriendNote(userId, reqDto);
    }

    @Operation(summary = "친구 메모 조회 API", description = "친구에게 남긴 메모를 조회합니다.")
    @GetMapping("/note")
    public ApiResponse<FriendRes.FriendNoteRes> getFriendNote(@RequestParam Long friendId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        return friendService.getFriendNote(userId, friendId);
    }

}

