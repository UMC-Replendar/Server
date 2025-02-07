package Umc.replendar.major.entity;

import Umc.replendar.global.BaseEntity;
import Umc.replendar.user.entity.AcademicYear;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecture")
public class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String lectureName;

    @Column(length = 50)
    private String professor;

    @Column(length = 50)
    private AcademicYear gradle;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;
}
