package com.example.back_end_java.entity.account;

import com.example.back_end_java.entity.account.Type.AccountType;
import com.example.back_end_java.entity.account.Type.Devise;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountResponse {
    private AccountType nameAccount;
    private int money;
    private String description;
    private Devise devise;
    private String email;
}
