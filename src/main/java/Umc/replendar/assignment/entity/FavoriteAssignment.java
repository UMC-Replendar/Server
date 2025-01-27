//package Umc.replendar.assignment.entity;
//
//import Umc.replendar.user.entity.User;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class FavoriteAssignment {
//
//    @Id
//    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "assignment_id")
//    private Assignment assignment;
//
//}
