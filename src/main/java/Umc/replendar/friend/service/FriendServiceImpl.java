package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.entity.Buddy;
import Umc.replendar.friend.entity.Friend;
import Umc.replendar.friend.repository.FriendRepository;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<String> addFriend(FriendReq reqDto) {
        User user = userRepository.findById(reqDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User friend = userRepository.findById(reqDto.getFriendId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구입니다."));

        // 이미 친구 관계인지 확인
        if (friendRepository.existsByUserAndFriend(user, friend)) {
            return ApiResponse.onFailure("FRIEND_ALREADY_EXISTS", "이미 친구로 등록된 사용자입니다.", null);
        }

        // 친구 관계 생성
        Friend newFriend = Friend.builder()
                .user(user)
                .friend(friend)
                .userBuddy(Buddy.NO)
                .friendBuddy(Buddy.NO)
                .userNote(reqDto.getUserNote())
                .build();
        friendRepository.save(newFriend);

        return ApiResponse.onSuccess("친구 등록이 완료되었습니다.");
    }
}
