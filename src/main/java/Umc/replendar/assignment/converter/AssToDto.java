package Umc.replendar.assignment.converter;

import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.global.function.TaskTimer;

import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public static AssignmentRes.assDetailRes toDetailDto(Assignment assignment){
        return AssignmentRes.assDetailRes.builder()
                .assId(assignment.getId())
                .title(assignment.getTitle())
                .due_date(assignment.getDueDate().format(DATE_TIME_FORMATTER))
                .notification(String.valueOf(assignment.getNotification()))
                .memo(assignment.getMemo())
                .notifyCycle(assignment.getNotifyCycle())
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
}
