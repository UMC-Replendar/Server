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

    @Operation(summary = "친구 등록 API", description = "친구를 등록합니다.")
    @PostMapping("")
    public ApiResponse<String> addFriend(@RequestBody FriendReq reqDto) {
        return friendService.addFriend(reqDto);
    }
    @Operation(summary = "친구 목록 조회 API", description = "친구 목록과 진행 중인 과제 개수를 조회합니다.")
    @GetMapping("")
    public ApiResponse<List<FriendRes.FriendListRes>> getFriends(@RequestParam Long userId) {
        return friendService.getFriends(userId);
    }

    @Operation(summary = "등록할 친구 검색 API", description = "닉네임으로 등록 가능한 친구를 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<FriendRes.FriendSearchRes> searchFriend(@RequestParam String nickname, @RequestParam Long userId) {
        return friendService.searchUserByNickname(nickname, userId);
    }
}

