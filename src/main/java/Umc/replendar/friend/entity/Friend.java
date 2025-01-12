package Umc.replendar.friend.entity;

import Umc.replendar.global.BaseEntity;
import Umc.replendar.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Buddy user_buddy;

    @Column
    private String user_note;

    @Column
    @Enumerated(EnumType.STRING)
    private Buddy friend_buddy;

    @Column
    private String friend_note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend_id;
}
