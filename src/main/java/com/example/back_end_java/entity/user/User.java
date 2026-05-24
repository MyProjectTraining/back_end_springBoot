package com.example.back_end_java.entity.user;


import com.example.back_end_java.entity.account.Account;
import com.example.back_end_java.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


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
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Product> products;
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Account> accounts;
}
