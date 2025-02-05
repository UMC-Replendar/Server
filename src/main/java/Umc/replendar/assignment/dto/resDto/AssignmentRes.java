package Umc.replendar.assignment.dto.resDto;

import Umc.replendar.assignment.entity.GeneralSettings;
import Umc.replendar.assignment.entity.NotifyCycle;
import Umc.replendar.assignment.entity.Status;
import Umc.replendar.user.entity.Active;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentRes {

    @Data
    @AllArgsConstructor
    @Builder
    public static class assMainTopRes{
        private Long assignmentId;
        private String title;
        private String visibility;
        private String notification;
        private String due_time;
        private String due_date;
        private String memo;
        private LocalDateTime completion_time;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class assDetailRes {
        private Long assId;
        private String title;
        private String due_date;
        private String memo;
        private GeneralSettings notification;
        private GeneralSettings visibility;
        private List<String> notifyCycle;
        private List<Map<Long,String>> shareFriend;
        private Active favorite;

//        private NotifyCycle notifyCycle;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class assMonthRes {
        private Long assId;
        private String title;
        private String due_date;
        private String due_time;
        private GeneralSettings notification;
        private Status status;
    }
    @Data
    @AllArgsConstructor
    @Builder
    public static class assShareRes {
        private Long userId;
        private String nickName;
        private String name;
        private String friendNote;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class assCompleteRes{
            private Long assId;
            private String title;
            private String due_date;
            private String due_time;
            private String due_datetime;
            private String memo;
            private Active favorite;
            private GeneralSettings notification;
            private GeneralSettings visibility;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class assLogRes{
        private Long assignmentId;
        private String title;
        private GeneralSettings visibility;
        private GeneralSettings notification;
        private String memo;
        private String due_date;
        private String due_time;
    }
}
