package Umc.replendar.assignment.entity;

import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.global.BaseEntity;
import Umc.replendar.major.entity.UserLectureAssignment;
import Umc.replendar.user.entity.Active;
import Umc.replendar.user.entity.User;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "assignment")
public class Assignment extends BaseEntity {

    @Builder
    public Assignment(String title, GeneralSettings visibility, Status status,  String memo, LocalDateTime due_date, LocalDateTime completion_time, List<ActivityLog> activityLogList, User user, GeneralSettings notification, Active favorite, Long originAssId) {

        this.user = user;
        this.title = title;
        this.dueDate = due_date;
        this.notification = notification;
        this.visibility = visibility;
        this.status = status;
        this.memo = memo;
        this.completionTime = completion_time;
        this.activityLogList = activityLogList;
        this.favorite = favorite;
        this.originAssId = originAssId;
    }

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_ass_id", nullable = true)
    private Long originAssId;  // 원본 과제 ID

    @Column(length = 35)
    private String title;

    @Enumerated(EnumType.STRING)
    private GeneralSettings visibility;

    @Enumerated(EnumType.STRING)
    private GeneralSettings notification;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @Enumerated(EnumType.STRING)
//    private GeneralSettings isActive;

    @Column(length = 254)
    private String memo;

    @Column
    private LocalDateTime dueDate;

    @Column
    private LocalDateTime completionTime;

    @Enumerated(EnumType.STRING)
    @Column
    private Active favorite;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REMOVE)
    private List<ActivityLog> activityLogList = new ArrayList<>();

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REMOVE)
    private List<Share> shareList = new ArrayList<>();

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REMOVE)
    private List<AssNotifyCycle> assNotifyCyclesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "assignment", cascade = CascadeType.REMOVE)
    private UserLectureAssignment userLectureAssignment;

    public GeneralSettings setNotification(String reqNotification) {
        switch (reqNotification) {
            case "ON":
                this.notification = GeneralSettings.ON;
                break;
            case "OFF":
                this.notification = GeneralSettings.OFF;
                break;
        }
        return notification;
    }

}
