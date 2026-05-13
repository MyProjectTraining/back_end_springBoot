package com.example.back_end_java.controller;

import com.example.back_end_java.entity.user.LoginRequest;
import com.example.back_end_java.entity.user.User;
import com.example.back_end_java.entity.UserLogin;
import com.example.back_end_java.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User save(@RequestBody  User user){
        return userService.creationUser(user);
    }

    @PostMapping("/login")
    public UserLogin login(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }

    @GetMapping("/auth/users")
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PutMapping("/auth/edit/{id}")
    public User updateUser(@RequestBody User user , @PathVariable Long id){
        return userService.editUser(id , user);
    }

    @DeleteMapping("/auth/delete/{id}")
    public void delete(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
