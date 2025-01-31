package Umc.replendar.assignment.entity;

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
public class AssNotifyCycle {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private NotifyCycle notifyCycle;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @OneToMany(mappedBy = "assNotifyCycle" , cascade = CascadeType.ALL)
    private List<NotifyLog> notifyLogList = new ArrayList<>();

}
