package Umc.replendar.assignment.dto.reqDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AssignmentReq {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReqDto{
        Long userId;
        String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
        LocalDateTime endDate;

        String notification;
        String visibility;
        List<Long> shareIds = new ArrayList<>();
        String memo;
    }


}
