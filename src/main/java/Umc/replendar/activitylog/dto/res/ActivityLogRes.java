package Umc.replendar.activitylog.dto.res;

import Umc.replendar.activitylog.entity.Check;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

public class ActivityLogRes {

    @Data
    @Builder
    @AllArgsConstructor
    public static class shareActivity{
        Long logId;
        Check check;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class getHistoryRes {
        String date;
        String time;
        Check check;
        Long friendId;
        Long assId;
        String content;
        LocalDateTime createdAt;
    }

//    @Data
//    @Builder
//    @AllArgsConstructor
//    public static class getActivityLogRes {
//
//    }
}
