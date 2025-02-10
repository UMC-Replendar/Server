package Umc.replendar.user.entity;

import Umc.replendar.activitylog.entity.ActivityLog;
import Umc.replendar.assignment.entity.Assignment;
import Umc.replendar.assignment.entity.NotifyLog;
import Umc.replendar.assignment.entity.Share;
import Umc.replendar.friend.entity.FriendRequest;
import Umc.replendar.friend.entity.friendship;
import Umc.replendar.global.BaseEntity;
import Umc.replendar.major.entity.Major;
import Umc.replendar.major.entity.UserLectureAssignment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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


    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 20, unique = true)
    private String nickname;

    @Column(length = 50)
    private String statusMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Theme theme;

    @PrePersist
    public void prePersist() {
        if (this.theme == null) {
            this.theme = Theme.DEFAULT;
        }
    }
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
    private List<ActivityLog> activityLogFriendList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Share> shareLogList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<NotifyLog> notifyLogList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserLectureAssignment> lectureAssignmentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Assignment> assignmentList = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    private List<FriendRequest> sentRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
    private List<FriendRequest> receivedRequests = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

}
