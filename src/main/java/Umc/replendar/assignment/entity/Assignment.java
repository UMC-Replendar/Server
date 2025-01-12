package Umc.replendar.assignment.entity;

import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.global.BaseEntity;
import Umc.replendar.user.entity.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Assignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    private GeneralSettings visibility;

    @Enumerated(EnumType.STRING)
    private GeneralSettings notification;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private GeneralSettings isActive;

    @Column(length = 254)
    private String memo;

    @Column
    private LocalDateTime due_date;

    @Column
    private LocalDateTime completion_time;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REMOVE)
    private List<ActivityLog> activityLogList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
