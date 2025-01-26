package Umc.replendar.user.entity;

import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.assignment.entity.Share;
import Umc.replendar.friend.entity.Friend;
import Umc.replendar.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private AcademicYear academicYear;

    @Column(length = 10)
    private String major;

    @Column(length = 100)
    private String email;

    @Column(length = 10, unique = true)
    @NotEmpty
    private String nickname;

    @Column(length = 254)
    private String profileImage;

    @Column(length = 50)
    private String statusMessage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Friend> friendUserList = new ArrayList<>();

    @OneToMany(mappedBy = "friend", cascade = CascadeType.REMOVE)
    private List<Friend> friendFriendList = new ArrayList<>();

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
