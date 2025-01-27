package Umc.replendar.global.function;

import Umc.replendar.assignment.entity.NotifyCycle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.HashMap;

public class TaskTimer {

    //완료시간에서 알림 주기를 계산해주는 함수
    public static LocalDateTime notifyCycle(LocalDateTime completionTime, NotifyCycle cycle) {
        LocalDateTime notifyTime = switch (cycle) {
            case DAY3 -> completionTime.minusDays(3);
            case DAY1 -> completionTime.minusDays(1);
            case H10 -> completionTime.minusHours(10);
            case H1 -> completionTime.minusHours(1);
        };
        return notifyTime;
    }

    //시간 빼주는 함수
    public static String taskTimer(LocalDateTime targetTime) {

        LocalDateTime now = LocalDateTime.now();

        // 남은 초를 기준으로 계산
        long totalSeconds = now.until(targetTime, ChronoUnit.SECONDS);
        long days = totalSeconds / (24 * 60 * 60);
        long hours = (totalSeconds % (24 * 60 * 60)) / (60 * 60);
        long minutes = (totalSeconds % (60 * 60)) / 60;
        long seconds = totalSeconds % 60;

        // 포맷된 문자열 반환
        return String.format("%dd %02dh %02dm %02ds", days, hours, minutes, seconds);

//        HashMap<String, Integer> time = new HashMap<>();
//        int years = (int) now.until(targetTime, ChronoUnit.YEARS);
//        int days = (int) now.until(targetTime, ChronoUnit.DAYS);
//        int hours = (int) now.until(targetTime, ChronoUnit.HOURS);
//        int months = (int) now.until(targetTime, ChronoUnit.MONTHS);
//        int minutes = (int) now.until(targetTime, ChronoUnit.MINUTES);
//        int seconds = (int) now.until(targetTime, ChronoUnit.SECONDS);
//
//        time.put("day", days);
//        time.put("hours" , hours);
//        time.put("minutes", minutes);
//        time.put("seconds", seconds);

//        return time;
    }
}
