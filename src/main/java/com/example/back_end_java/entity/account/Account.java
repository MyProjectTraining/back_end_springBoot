package com.example.back_end_java.entity.account;

import com.example.back_end_java.entity.Type.AccountType;
import com.example.back_end_java.entity.Type.Devise;
import com.example.back_end_java.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "account")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    @Id
    private AccountType accountType;
    @Min(value = 0)
    private int money;
    private String description;
    private Devise devise;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
