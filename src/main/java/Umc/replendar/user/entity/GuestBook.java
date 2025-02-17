package Umc.replendar.user.entity;

import Umc.replendar.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class GuestBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 30)
    private String teamName;

    @Column(length = 30)
    private String teamPart;

    private String content;
}
