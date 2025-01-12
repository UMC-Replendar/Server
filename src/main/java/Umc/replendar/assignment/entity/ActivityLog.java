package Umc.replendar.assignment.entity;

import Umc.replendar.global.BaseEntity;
import Umc.replendar.user.entity.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.sql.ast.tree.update.Assignment;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ActivityLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Action action;

    @Enumerated(EnumType.STRING)
    private Check check;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}
