package Umc.replendar.activitylog.dto.res;

import Umc.replendar.activitylog.entity.Check;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    //히스토리 조회
    @Data
    @Builder
    @AllArgsConstructor
//    @JsonInclude(JsonInclude.Include.NON_NULL) //-> 와우!!
    public static class getHistoryRes {
        Long assignmentNotifyId;   // 과제 알림 ID (과제 알림인 경우)
        Long friendRequestId;      // 친구 요청 ID (친구 요청인 경우)
        Long senderId;             // 친구 요청을 보낸 사람 ID (친구 요청인 경우)
        Long assId;                // 과제 ID (과제 활동인 경우)
        Long friendId;             // 친구 ID (친구 요청인 경우)
        Long activityLogId;        // 활동 로그 ID (활동 로그인 경우)
        String date;
        String time;
        Check check;
        String content;
        LocalDateTime createdAt;
        String type;
        //private boolean isRegistered;
    }
    //히스토리 조회(친구 소식)
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) //-> 와우!!
    public static class FriendActivityHistoryRes {
        Long friendRequestId;      // 친구 요청 ID (친구 요청인 경우)
        Long senderId;             // 친구 요청을 보낸 사람 ID (친구 요청인 경우)
        Long friendId;             // 친구 ID (과제 활동인 경우)
        Long assId;                // 과제 ID (과제 활동인 경우)
        Check check;
        String date;
        String time;
        String content;
        LocalDateTime createdAt;
        String timeStamp;          // (예: "5분 전")
        String type;               // (친구요청 / 과제)
        Boolean isRegistered;      // 과제 등록 여부 (과제 활동인 경우)
    }
}
