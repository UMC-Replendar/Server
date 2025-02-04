package Umc.replendar.activitylog.converter;

import Umc.replendar.activitylog.dto.res.ActivityLogRes;
import Umc.replendar.activitylog.entity.Action;
import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.assignment.entity.NotifyLog;
import Umc.replendar.friend.entity.FriendRequest;

import java.time.format.DateTimeFormatter;

import static Umc.replendar.assignment.entity.NotifyCycle.DAY3;

public class logConverter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ActivityLogRes.getHistoryRes activityLogHistoryDto(ActivityLog activityLog) {
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

    public static ActivityLogRes.getHistoryRes notifyLogHistoryDto(NotifyLog notifyLog) {

        String content = "";

        switch (notifyLog.getAssNotifyCycle().getNotifyCycle()) {
            case DAY3:
                content = "3일";
            case DAY1:
                content = "1일";
            case H1:
                content = "1시간";
            case H10:
                content = "10시간";
        }

        return ActivityLogRes.getHistoryRes.builder()
                .date(notifyLog.getCreatedAt().format(DATE_FORMATTER))
                .time(notifyLog.getCreatedAt().format(TIME_FORMATTER))
                .check(notifyLog.getIsCheck())
                .assId(notifyLog.getAssNotifyCycle().getAssignment().getId())
                .content(notifyLog.getAssNotifyCycle().getAssignment().getTitle() + "과제 마감까지" + content + "남았습니다.")
                .createdAt(notifyLog.getCreatedAt())
                .build();
    }

    public static ActivityLogRes.getHistoryRes friendRequestHistoryDto(FriendRequest fr){
        Check check = null;
        switch (fr.getStatus()){
            case PENDING:
                check = Check.UNCHECK;
                break;
            case ACCEPTED:
                check = Check.CHECK;
                break;
            case REJECTED:
                check = Check.CHECK;
                break;
        }

        return ActivityLogRes.getHistoryRes.builder()
                .date(fr.getCreatedAt().format(DATE_FORMATTER))
                .time(fr.getCreatedAt().format(TIME_FORMATTER))
                .check(check)
                .friendId(fr.getSender().getId())
                .content(fr.getSender().getNickname()+"님이 친구 요청을 보냈습니다.")
                .createdAt(fr.getCreatedAt())
                .build();
    }


}
