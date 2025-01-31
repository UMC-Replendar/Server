package Umc.replendar.activitylog.converter;

import Umc.replendar.activitylog.dto.res.ActivityLogRes;
import Umc.replendar.activitylog.entity.Action;
import Umc.replendar.activitylog.entity.ActivityLog;

import java.time.format.DateTimeFormatter;

public class logConverter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ActivityLogRes.getHistoryRes convertCheckToString(ActivityLog activityLog) {
        String content = "";
        switch (activityLog.getAction()){
            case SHARE:
                content = activityLog.getFriend().getNickname()+"님이 "+ activityLog.getAssignment().getTitle()+" 과제를 공유하였습니다.";
                break;
            case COMPLETE:
                content = activityLog.getFriend().getNickname()+"님이 "+ activityLog.getAssignment().getTitle()+" 과제를 완료하였습니다.";
                break;
            case ADD_ASS:
                content = activityLog.getFriend().getNickname()+"님이 "+ activityLog.getAssignment().getTitle()+" 과제를 추가하였습니다.";
                break;
        }

        return ActivityLogRes.getHistoryRes.builder()
                .date(activityLog.getCreatedAt().format(DATE_FORMATTER))
                .time(activityLog.getCreatedAt().format(TIME_FORMATTER))
                .check(activityLog.getIsCheck())
                .friendId(activityLog.getFriend().getId())
                .assId(activityLog.getAssignment().getId())
                .content(content)
                .createdAt(activityLog.getCreatedAt())
                .build();

    }


}
