package com.example.back_end_java.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;


@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(nullable = false , unique = true)
    private String email;
    private String role;
    private String password;
}
