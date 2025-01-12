package Umc.replendar.user.entity;

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
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    @NotEmpty
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private AcademicYear academic_year;

    @Column(length = 10)
    private String major;

    @Column(length = 100)
    private String email;

    @Column(length = 10, unique = true)
    private String nickname;

    @Column(length = 255)
    private String profile_image;

    @Column(length = 50)
    private String status_message;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Friend> user_id = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Friend> friend_id = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;


}
