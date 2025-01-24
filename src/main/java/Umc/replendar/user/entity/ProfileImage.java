package Umc.replendar.user.entity;

import Umc.replendar.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String originalFilename;

    @Column
    private String contentType;

    @Column
    private Long fileSize;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
