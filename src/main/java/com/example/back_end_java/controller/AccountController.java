package com.example.back_end_java.controller;

import com.example.back_end_java.entity.account.AccountRequest;
import com.example.back_end_java.entity.account.AccountResponse;
import com.example.back_end_java.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/user/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AccountResponse saveUser(@PathVariable String email,@RequestBody AccountRequest accountRequest){
        return accountService.creationAccount(accountRequest , email);
    }

    @GetMapping("/user/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> getAccountUser(@PathVariable String email){
        return accountService.getAll(email);
    }
}
