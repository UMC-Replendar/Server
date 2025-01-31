package Umc.replendar.friend.entity;

import Umc.replendar.global.BaseEntity;
import Umc.replendar.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FriendGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 그룹 ID

    @Column(nullable = false)
    private String name;  // 그룹명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 그룹을 생성한 사용자

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendGroupMember> members = new ArrayList<>();
}
