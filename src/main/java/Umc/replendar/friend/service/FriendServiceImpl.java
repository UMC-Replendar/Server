package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.entity.GeneralSettings;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.friend.converter.FriToDto;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.entity.Buddy;
import Umc.replendar.friend.entity.Friend;
import Umc.replendar.friend.repository.FriendRepository;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    //친구 등록.
    @Override
    public ApiResponse<String> addFriend(FriendReq reqDto) {
        User user = userRepository.findById(reqDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User friend = userRepository.findById(reqDto.getFriendId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구입니다."));


        // 이미 친구 관계인지 확인
        boolean isFriendExists = friendRepository.existsByUserAndFriend(user, friend)
                || friendRepository.existsByUserAndFriend(friend, user);

        if (isFriendExists) {
            return ApiResponse.onFailure("FRIEND_ALREADY_EXISTS", "이미 친구로 등록된 사용자입니다.", null);
        }

        // 친구 테이블 데이터를 삽입할 때 항상 userId < friendId 순서로 저장되도록.
        User smallerUser = (user.getId() < friend.getId()) ? user : friend;
        User largerUser = (user.getId() > friend.getId()) ? user : friend;

        // 친구 관계 생성
        Friend newFriend = Friend.builder()
                .user(smallerUser)
                .friend(largerUser)
                .userBuddy(Buddy.NO)
                .friendBuddy(Buddy.NO)
                .build();
        friendRepository.save(newFriend);

        return ApiResponse.onSuccess("친구 등록이 완료되었습니다.");
    }

    //친구 목록 조회.
    @Override
    public ApiResponse<List<FriendRes.FriendListRes>> getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<User> friends = friendRepository.findFriendsByUserId(userId);

        // 친구별 진행 중인 과제 개수 계산
        List<FriendRes.FriendListRes> friendList = friends.stream()
                .map(friend -> {
                    // 진행 중인 과제 개수 조회(visibility가 ON인 것만.)
                    int ongoingAssignments = assignmentRepository.countByUserAndStatusAndVisibility(friend, Status.ONGOING, GeneralSettings.ON);

                    return FriToDto.toFriendListRes(friend, ongoingAssignments);
                })
                // 친구 닉네임으로 사전순 정렬
                .sorted(Comparator.comparing(FriendRes.FriendListRes::getNickname))
                .toList();

        return ApiResponse.onSuccess(friendList);
    }

    //친구로 등록할 닉네임 검색.
    @Override
    public ApiResponse<FriendRes.FriendSearchRes> searchUserByNickname(String nickname, Long userId) {
        // 닉네임으로 사용자 검색
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 자기 자신인지 확인
        if (user.getId().equals(userId)) {
            return ApiResponse.onFailure("INVALID_REQUEST", "자기 자신은 검색할 수 없습니다.", null);
        }

        // 이미 친구 관계인지 확인
        boolean isFriendExists = friendRepository.existsByUserAndFriend(
                userRepository.findById(Math.min(userId, user.getId())).get(),
                userRepository.findById(Math.max(userId, user.getId())).get()
        );

        if (isFriendExists) {
            return ApiResponse.onFailure("FRIEND_ALREADY_EXISTS", "이미 친구로 등록된 사용자입니다.", null);
        }

        FriendRes.FriendSearchRes friendSearchRes = FriToDto.toFriendSearchRes(user);

        return ApiResponse.onSuccess(friendSearchRes);
    }
}
