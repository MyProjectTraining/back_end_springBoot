package com.example.back_end_java.service;

import com.example.back_end_java.entity.User;
import com.example.back_end_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User creationUser(User user) throws RuntimeException{
          String email = user.getEmail();
          User existedEmail = userRepository.findByEmail(email);
             if (existedEmail != null){
                 throw new RuntimeException("email already existing");
             }
             String hasPassword = passwordEncoder.encode(user.getPassword());
             user.setPassword(hasPassword);

            return  userRepository.save(user);
    }
}
