package Umc.replendar.major.entity;

import Umc.replendar.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lecture_assignment")
public class LectureAssignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 254)
    private String content;

    @Column(length = 50)
    private String answer;

    @Column
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToMany(mappedBy = "lectureAssignment", cascade = CascadeType.REMOVE)
    private List<UserLectureAssignment> lectureAssignmentList = new ArrayList<>();

}
