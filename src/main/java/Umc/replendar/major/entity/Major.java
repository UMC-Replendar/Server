package Umc.replendar.major.entity;

import Umc.replendar.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "major")
public class Major {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String majorName;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "major", cascade = CascadeType.REMOVE)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "major", cascade = CascadeType.REMOVE)
    private List<Lecture> lectures = new ArrayList<>();
}
