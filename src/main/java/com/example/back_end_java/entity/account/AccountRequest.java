package com.example.back_end_java.entity.account;

import com.example.back_end_java.entity.Type.AccountType;
import com.example.back_end_java.entity.Type.Devise;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountRequest {
    private AccountType accountType;
    private int money;
    private String description;
    private Devise devise;
}
