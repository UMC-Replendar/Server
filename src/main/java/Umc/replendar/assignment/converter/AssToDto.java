package Umc.replendar.assignment.converter;

import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.friend.entity.friendship;
import Umc.replendar.user.entity.User;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static Umc.replendar.global.function.TaskTimer.taskTimer;

public class AssToDto {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static List<AssignmentRes.assMainTopRes> toMainTopDto(List<Assignment> assignmentList){

        return assignmentList.stream().map(
                assignment ->
                        AssignmentRes.assMainTopRes.builder()
                        .assignmentId(assignment.getId())
                        .title(assignment.getTitle())
                        .due_time(taskTimer((assignment.getDueDate())))
                        .due_date(assignment.getDueDate().format(DATE_FORMATTER))
                        .notification(String.valueOf(assignment.getNotification()))
                        .visibility(String.valueOf(assignment.getVisibility()))
                        .build())
                .toList();
    }

    public static Page<AssignmentRes.assCompleteRes> toDetailPageDto(Page<Assignment> assignmentPage){

        return assignmentPage.map(assignment ->
                AssignmentRes.assCompleteRes.builder()
                        .assId(assignment.getId())
                        .title(assignment.getTitle())
                        .due_datetime(taskTimer((assignment.getDueDate())))
                        .due_date(assignment.getDueDate().format(DATE_FORMATTER))
                        .due_time(assignment.getDueDate().format(TIME_FORMATTER))
                        .memo(assignment.getMemo())
                        .notification(assignment.getNotification())
                        .visibility(assignment.getVisibility())
                        .favorite(assignment.getFavorite())
                        .createdAt(assignment.getCreatedAt())
                        .updatedAt(assignment.getUpdatedAt())
                        .build()
        );
    }

    public static AssignmentRes.assDetailRes toDetailDto(Assignment assignment, List<String> notifyCycleList, List<User> shareUserList){
        List<Map<Long,String>> shareFriendList2 = shareUserList.stream().map(
                user -> Map.of(user.getId(), user.getNickname())
        ).toList();
        return AssignmentRes.assDetailRes.builder()
                .assId(assignment.getId())
                .title(assignment.getTitle())
                .due_date(assignment.getDueDate().format(DATE_TIME_FORMATTER))
                .notification(assignment.getNotification())
                .visibility(assignment.getVisibility())
                .notifyCycle(notifyCycleList)
                .shareFriend(shareFriendList2)
                .memo(assignment.getMemo())
                .favorite(assignment.getFavorite())
                .build();
    }

    public static List<AssignmentRes.assMonthRes> toMonthDto(List<Assignment> assignmentList){
        return assignmentList.stream().map(
                    assignment1 ->
                        AssignmentRes.assMonthRes.builder()
                        .assId(assignment1.getId())
                        .title(assignment1.getTitle())
                        .due_date(assignment1.getDueDate().format(DATE_FORMATTER))
                        .due_time(assignment1.getDueDate().format(TIME_FORMATTER))
                        .notification(assignment1.getNotification())
                        .status(assignment1.getStatus())
                        .build()
                ).toList();
    }

    public static AssignmentRes.assShareRes toShareUserDto(Boolean isFriend, friendship friendship){

        //UserId가 친구 Id일경우
        if(isFriend){
            return AssignmentRes.assShareRes.builder()
                    .userId(friendship.getUser().getId())
                    .nickName(friendship.getUser().getNickname())
                    .name(friendship.getUser().getName())
                    .friendNote(friendship.getFriendNote())
                    .build();
        }

        //friendId가 친구 id일경우
        return AssignmentRes.assShareRes.builder()
                .userId(friendship.getFriend().getId())
                .nickName(friendship.getFriend().getNickname())
                .name(friendship.getFriend().getName())
                .friendNote(friendship.getUserNote())
                .build();
    }
}
