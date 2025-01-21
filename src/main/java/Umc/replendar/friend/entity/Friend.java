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
    private Buddy userBuddy;

    @Column
    private String userNote;

    @Column
    @Enumerated(EnumType.STRING)
    private Buddy friendBuddy;

    @Column
    private String friendNote;

    @ManyToOne
    @JoinColumn(name = "user_id") // Friend를 생성한 사용자
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id") // Friend가 가리키는 친구 사용자
    private User friend;
}
