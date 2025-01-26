package Umc.replendar.friend.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @Operation(summary = "친구 등록 API", description = "친구를 등록합니다.")
    @PostMapping("")
    public ApiResponse<String> addFriend(@RequestBody FriendReq reqDto) {
        friendService.addFriend(reqDto);
        return new ApiResponse<>(true, "COMMON200", "친구 등록 성공", "친구가 등록되었습니다.");
    }
}

