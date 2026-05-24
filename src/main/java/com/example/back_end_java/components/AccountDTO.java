package com.example.back_end_java.components;

import com.example.back_end_java.entity.account.Account;
import com.example.back_end_java.entity.account.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountDTO {
    public AccountResponse DTO(Account account){
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setNameAccount(account.getAccountType());
        accountResponse.setDescription(account.getDescription());
        accountResponse.setMoney(account.getMoney());
        accountResponse.setDevise(account.getDevise());
        if (account.getUser() != null){
            accountResponse.setEmail(account.getUser().getEmail());
        }
        return accountResponse;
    }
}
