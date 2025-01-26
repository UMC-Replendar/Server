package Umc.replendar.assignment.dto.reqDto;

import Umc.replendar.assignment.entity.NotifyCycle;
import Umc.replendar.user.entity.Active;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AssignmentReq {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReqDto{
        Long userId;
        String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
        LocalDateTime endDate;

        String notification;
        String visibility;
        NotifyCycle notifyCycle;
        List<Long> shareIds;
        String memo;
        SaveType saveType;
        Active active;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateReqDto{
        Long assId;
        String title;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
        LocalDateTime endDate;
        String notification;
        NotifyCycle notifyCycle;
        String visibility;

        String memo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreReqDto{
        Long userId;
        String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
        LocalDateTime endDate;

        String notification;
        String visibility;
        NotifyCycle notifyCycle;
        List<Long> shareIds;
        String memo;
    }


}
