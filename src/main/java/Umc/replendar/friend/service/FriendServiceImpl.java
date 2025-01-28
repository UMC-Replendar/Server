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
import Umc.replendar.friend.entity.FriendRequest;
import Umc.replendar.friend.entity.RequestStatus;
import Umc.replendar.friend.repository.FriendRepository;
import Umc.replendar.friend.repository.FriendRequestRepository;
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
    private final FriendRequestRepository friendRequestRepository;

// 친구 요청 생성
    @Override
    public ApiResponse<Long> sendFriendRequest(FriendReq.FriendRequestDto reqDto) {
        User sender = userRepository.findById(reqDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        User receiver = userRepository.findById(reqDto.getFriendId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구입니다."));

        // 자기 자신에게 요청하는 경우 처리
        if (sender.getId().equals(receiver.getId())) {
            return ApiResponse.onFailure("INVALID_REQUEST", "자기 자신에게 친구 요청을 보낼 수 없습니다.", null);
        }

        // 이미 친구 관계인지 확인
        boolean isFriendExists = friendRepository.existsByUserAndFriend(
                userRepository.findById(Math.min(sender.getId(), receiver.getId())).get(),
                userRepository.findById(Math.max(sender.getId(), receiver.getId())).get()
        );

        if (isFriendExists) {
            return ApiResponse.onFailure("FRIEND_ALREADY_EXISTS", "이미 친구로 등록된 사용자입니다.", null);
        }

        // 이미 친구 요청이 존재하는지 확인
        boolean isRequestExists = friendRequestRepository.existsBySenderAndReceiver(sender, receiver)
                || friendRequestRepository.existsBySenderAndReceiver(receiver, sender);

        if (isRequestExists) {
            return ApiResponse.onFailure("REQUEST_ALREADY_EXISTS", "이미 친구 요청이 존재합니다.", null);
        }

        // 친구 요청 생성
        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(RequestStatus.PENDING) // 상태: 요청 대기중
                .build();

        // 저장된 요청을 변수에 저장
        FriendRequest savedRequest = friendRequestRepository.save(friendRequest);

        // 생성된 요청 ID 반환
        return ApiResponse.onSuccess(savedRequest.getId());
    }

    // 친구 요청 수락
    @Override
    public ApiResponse<String> acceptFriendRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 요청입니다."));

        if (friendRequest.getStatus() != RequestStatus.PENDING) {
            return ApiResponse.onFailure("INVALID_REQUEST", "이미 처리된 친구 요청입니다.", null);
        }

        // 요청 수락
        friendRequest.setStatus(RequestStatus.ACCEPTED);
        friendRequestRepository.save(friendRequest);

        // 친구 관계 생성
        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();

        User smallerUser = (sender.getId() < receiver.getId()) ? sender : receiver;
        User largerUser = (sender.getId() > receiver.getId()) ? sender : receiver;

        Friend newFriend = Friend.builder()
                .user(smallerUser)
                .friend(largerUser)
                .userBuddy(Buddy.NO)
                .friendBuddy(Buddy.NO)
                .build();
        friendRepository.save(newFriend);

        return ApiResponse.onSuccess("친구 요청을 수락했습니다.");
    }

    // 친구 요청 거절
    @Override
    public ApiResponse<String> rejectFriendRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 요청입니다."));

        if (friendRequest.getStatus() != RequestStatus.PENDING) {
            return ApiResponse.onFailure("INVALID_REQUEST", "이미 처리된 친구 요청입니다.", null);
        }

        // 요청 거절
        friendRequest.setStatus(RequestStatus.REJECTED);
        friendRequestRepository.save(friendRequest);

        return ApiResponse.onSuccess("친구 요청을 거절했습니다.");
    }
    //친구 목록 조회.
    @Override
    public ApiResponse<List<FriendRes.FriendListRes>> getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<Friend> friends = friendRepository.findAllByUserIdOrFriendId(userId, userId);

        List<FriendRes.FriendListRes> friendList = friends.stream()
                .map(friend -> {
                    int ongoingAssignments = assignmentRepository.countByUserAndStatusAndVisibility(friend.getFriendForUser(userId), Status.ONGOING, GeneralSettings.ON);
                    return FriToDto.toFriendListRes(friend, userId, ongoingAssignments);
                })
                .sorted(Comparator.comparing((FriendRes.FriendListRes f) -> f.getBuddyStatus() == Buddy.YES ? 0 : 1)
                        .thenComparing(FriendRes.FriendListRes::getNickname))
                .toList();

        return ApiResponse.onSuccess(friendList);
    }
    //친구 목록 조회 top5
    @Override
    public ApiResponse<List<FriendRes.FriendListRes>> getTop5Friends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<Friend> friends = friendRepository.findAllByUserIdOrFriendId(userId, userId);

        List<FriendRes.FriendListRes> friendList = friends.stream()
                .map(friend -> {
                    int ongoingAssignments = assignmentRepository.countByUserAndStatusAndVisibility(friend.getFriendForUser(userId), Status.ONGOING, GeneralSettings.ON);
                    return FriToDto.toFriendListRes(friend, userId, ongoingAssignments);
                })
                .sorted(Comparator.comparing((FriendRes.FriendListRes f) -> f.getBuddyStatus() == Buddy.YES ? 0 : 1)
                        .thenComparing(FriendRes.FriendListRes::getNickname))
                .limit(5)
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
