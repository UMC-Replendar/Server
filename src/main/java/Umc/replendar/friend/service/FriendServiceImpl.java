package Umc.replendar.friend.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.entity.GeneralSettings;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.assignment.repository.AssignmentRepository;
import Umc.replendar.friend.converter.FriToDto;
import Umc.replendar.friend.dto.reqDto.FriendReq;
import Umc.replendar.friend.dto.resDto.FriendRes;
import Umc.replendar.friend.entity.Buddy;
import Umc.replendar.friend.entity.friendship;
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
import java.util.Optional;

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

        // 이미 친구 요청이 존재하는지 확인 및 요청 ID 반환
        Optional<FriendRequest> existingRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver)
                .or(() -> friendRequestRepository.findBySenderAndReceiver(receiver, sender));

        if (existingRequest.isPresent()) {
            return ApiResponse.onFailure(
                    "REQUEST_ALREADY_EXISTS",
                    "이미 친구 요청이 존재합니다.",
                    existingRequest.get().getId() // 기존 요청의 ID를 반환
            );
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

        friendship newFriendship = friendship.builder()
                .user(smallerUser)
                .friend(largerUser)
                .userBuddy(Buddy.NO)
                .friendBuddy(Buddy.NO)
                .build();
        friendRepository.save(newFriendship);

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
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<friendship> friendships = friendRepository.findAllByUserIdOrFriendId(userId, userId);

        List<FriendRes.FriendListRes> friendList = friendships.stream()
                .map(friend -> {
                    int ongoingAssignments = assignmentRepository.countByUserAndStatusAndVisibility(friend.getFriendForUser(userId), Status.ONGOING, GeneralSettings.ON);
                    return FriToDto.toFriendListRes(friend, userId, ongoingAssignments);
                })
                .sorted(Comparator.comparing((FriendRes.FriendListRes f) -> f.getBuddyStatus() == Buddy.YES ? 0 : 1)
                        .thenComparing(FriendRes.FriendListRes::getNickname))
                .toList();

        return ApiResponse.onSuccess(friendList);
    }
    //친구 목록 조회 (개수 설정)
    @Override
    public ApiResponse<List<FriendRes.FriendListRes>> getTopFriends(Long userId,int limit) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<friendship> friendships = friendRepository.findAllByUserIdOrFriendId(userId, userId);

        List<FriendRes.FriendListRes> friendList = friendships.stream()
                .map(friend -> {
                    int ongoingAssignments = assignmentRepository.countByUserAndStatusAndVisibility(friend.getFriendForUser(userId), Status.ONGOING, GeneralSettings.ON);
                    return FriToDto.toFriendListRes(friend, userId, ongoingAssignments);
                })
                .sorted(Comparator.comparing((FriendRes.FriendListRes f) -> f.getBuddyStatus() == Buddy.YES ? 0 : 1)
                        .thenComparing(FriendRes.FriendListRes::getNickname))
                .limit(limit)
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
    // 친한 친구 관계 설정.
    @Override
    public ApiResponse<String> updateBestFriend(FriendReq.FriendBuddyReqDto reqDto) {
        Long userId = reqDto.getUserId();
        Long friendId = reqDto.getFriendId();
        Buddy buddyStatus = reqDto.getBuddyStatus();

        friendship friendship = friendRepository.findFriendByUserAndFriend(userId, friendId)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

        // userId가 user 컬럼이면 userBuddy 변경, friend 컬럼이면 friendBuddy 변경
        if (friendship.getUser().getId().equals(userId)) {
            friendship.setUserBuddy(buddyStatus);
        } else {
            friendship.setFriendBuddy(buddyStatus);
        }

        friendRepository.save(friendship);
        return ApiResponse.onSuccess("친한 친구 상태가 변경되었습니다.");
    }
    //친구 관계 삭제
    @Override
    public ApiResponse<String> deleteFriend(Long userId, Long friendId) {
        friendship friendship = friendRepository.findFriendByUserAndFriend(userId, friendId)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

        // 친구 요청 삭제.
        Optional<FriendRequest> existingRequest = friendRequestRepository.findBySenderAndReceiver(
                        friendship.getUser(), friendship.getFriend())
                .or(() -> friendRequestRepository.findBySenderAndReceiver(
                        friendship.getFriend(), friendship.getUser()));

        if (existingRequest.isPresent()) {
            FriendRequest friendRequest = existingRequest.get();
            friendRequestRepository.delete(friendRequest);
        }

        friendRepository.delete(friendship);

        return ApiResponse.onSuccess("친구 관계가 삭제되었습니다.");
    }
}
