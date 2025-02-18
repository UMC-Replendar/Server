package Umc.replendar.common;

import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.assignment.entity.GeneralSettings;
import Umc.replendar.assignment.entity.NotifyLog;
import Umc.replendar.assignment.repository.AssNotifyCycleRepository;
import Umc.replendar.assignment.repository.NotifyLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final AssNotifyCycleRepository assNotifyCycleRepository;
    private final NotifyLogRepository notifyLogRepository;

    //1분마다 실행
    @Scheduled(cron = "0 * * * * ?")
    public void createdNotifyLog() {
        assNotifyCycleRepository.findAllByScheduledAtBeforeAndNotifyCheck(LocalDateTime.now(), GeneralSettings.OFF)
                .forEach(assNotifyCycle -> {

                    notifyLogRepository.save(NotifyLog.builder()
                            .user(assNotifyCycle.getAssignment().getUser())
                            .assNotifyCycle(assNotifyCycle)
                            .isCheck(Check.UNCHECK)
                            .build());

                    assNotifyCycle.setNotifyCheck(GeneralSettings.ON);
                    assNotifyCycleRepository.save(assNotifyCycle);
                });
    }
}
