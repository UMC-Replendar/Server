package Umc.replendar.user.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.user.dto.req.GuestBookDtoReq;
import Umc.replendar.user.dto.res.GuestBookDtoRes;
import Umc.replendar.user.service.GuestBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guestbook")
@RequiredArgsConstructor
public class GuestBookController {
    
    private final GuestBookService guestBookService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    @Operation(summary = "방명록 작성 API", description = "방명록을 작성")
    public ApiResponse<String> createGuestBook(@RequestBody GuestBookDtoReq requestDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        guestBookService.createGuestBook(userId, requestDto);
        return ApiResponse.onSuccess("방명록이 작성되었습니다.");
    }

    @GetMapping
    @Operation(summary = "방명록 전체 조회 API", description = "방명록을 전체 조회")
    public List<GuestBookDtoRes> getAllGuestBooks() {
        return guestBookService.getAllGuestBooks();
    }
}
