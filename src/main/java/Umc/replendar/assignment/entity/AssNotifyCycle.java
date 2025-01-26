package Umc.replendar.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AssNotifyCycle {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime notifyTime;

    @Column
    @Enumerated(EnumType.STRING)
    private NotifyCycle notifyCycle;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}
