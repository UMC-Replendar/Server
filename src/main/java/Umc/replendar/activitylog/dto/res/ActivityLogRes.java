package Umc.replendar.activitylog.dto.res;

import Umc.replendar.activitylog.entity.Check;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
        String nickname;
        String friend;
        String content;
        Map<TypeId, Long> type;
    }

//    @Data
//    @Builder
//    @AllArgsConstructor
//    public static class getActivityLogRes {
//
//    }
}
