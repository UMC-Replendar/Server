package Umc.replendar.assignment.dto.resDto;

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
