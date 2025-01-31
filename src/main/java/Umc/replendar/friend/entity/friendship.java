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
public class friendship extends BaseEntity {

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendGroupMember> groupMemberships = new ArrayList<>();

    // 사용자 ID에 따른 Buddy 상태를 반환하는 메서드
    public Buddy getBuddyStatusForUser(Long userId) {
        if (this.user.getId().equals(userId)) {
            return this.userBuddy;
        } else if (this.friend.getId().equals(userId)) {
            return this.friendBuddy;
        }
        throw new IllegalArgumentException("해당 사용자는 친구 관계에 포함되지 않습니다.");
    }

    // 사용자 ID에 따른 상대 친구 정보를 반환하는 메서드
    public User getFriendForUser(Long userId) {
        if (this.user.getId().equals(userId)) {
            return this.friend;
        } else if (this.friend.getId().equals(userId)) {
            return this.user;
        }
        throw new IllegalArgumentException("해당 사용자는 친구 관계에 포함되지 않습니다.");
    }

}
