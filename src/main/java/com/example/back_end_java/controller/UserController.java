package com.example.back_end_java.controller;

import com.example.back_end_java.entity.LoginRequest;
import com.example.back_end_java.entity.User;
import com.example.back_end_java.entity.UserLogin;
import com.example.back_end_java.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/create")
    public User save(@RequestBody  User user){
        return userService.creationUser(user);
    }

    @PostMapping("/api/login")
    public UserLogin login(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }
}
