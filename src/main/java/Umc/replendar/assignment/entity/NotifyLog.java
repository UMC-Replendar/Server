package Umc.replendar.assignment.entity;

import Umc.replendar.activitylog.entity.Check;
import Umc.replendar.global.BaseEntity;
import Umc.replendar.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotifyLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Check isCheck;

    @ManyToOne
    @JoinColumn(name = "assNotifyCycle_id")
    private AssNotifyCycle assNotifyCycle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
