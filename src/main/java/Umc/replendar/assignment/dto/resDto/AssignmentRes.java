package Umc.replendar.assignment.dto.resDto;

import Umc.replendar.assignment.entity.GeneralSettings;
import Umc.replendar.assignment.entity.NotifyCycle;
import Umc.replendar.assignment.entity.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;

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
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class assDetailRes {
        private Long assId;
        private String title;
        private String due_date;
        private String memo;
        private String notification;
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

//    @Setter
//    @AllArgsConstructor
//    @Builder
//    public static class assRes{
//        private Long id;
//        private String title;
//        private String visibility;
//        private String notification;
//        private LocalDateTime due_date;
//    }
}
