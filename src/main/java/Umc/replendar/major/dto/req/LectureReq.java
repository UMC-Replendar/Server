package Umc.replendar.major.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

public class LectureReq {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LectureAssignmentPostReq {
        private Long lectureId;
        private String title;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDate endDate;
    }
}
