package Umc.replendar.friend.entity;

import Umc.replendar.global.BaseEntity;
import Umc.replendar.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FriendRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 요청 ID

    @ManyToOne
    private User sender; // 요청 보낸 사용자

    @ManyToOne
    private User receiver; // 요청 받은 사용자

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // 요청 상태 (PENDING, ACCEPTED, REJECTED)
}
