package Umc.replendar.assignment.converter;

import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.global.function.TaskTimer;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static Umc.replendar.global.function.TaskTimer.taskTimer;

public class AssToDto {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static List<AssignmentRes.assMainTopRes> toMainTopDto(List<Assignment> assignmentList){

        return assignmentList.stream().map(
                assignment ->
                        AssignmentRes.assMainTopRes.builder()
                        .assignmentId(assignment.getId())
                        .title(assignment.getTitle())
                        .due_time(taskTimer((assignment.getDueDate())))
                        .due_date(assignment.getDueDate().format(FORMATTER))
                        .notification(String.valueOf(assignment.getNotification()))
                        .visibility(String.valueOf(assignment.getVisibility()))
                        .build())
                .toList();
    }
}
