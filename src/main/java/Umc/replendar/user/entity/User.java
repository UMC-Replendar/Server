package Umc.replendar.user.entity;

import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.assignment.entity.Share;
import Umc.replendar.friend.entity.friendship;
import Umc.replendar.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private AcademicYear academicYear;

    @Column(length = 20)
    private String major;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 20, unique = true)
    private String nickname;

    @Column(length = 50)
    private String statusMessage;

//    @Column
//    private String password;


    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<friendship> friendshipUserList = new ArrayList<>();

    @OneToMany(mappedBy = "friend", cascade = CascadeType.REMOVE)
    private List<friendship> friendFriendshipList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ActivityLog> activityLogList = new ArrayList<>();

    @OneToMany(mappedBy = "friend", cascade = CascadeType.REMOVE)
    private List<ActivityLog> acitivtyLogList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Share> shareLogList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

}
