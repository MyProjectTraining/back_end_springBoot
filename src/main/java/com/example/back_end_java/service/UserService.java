package com.example.back_end_java.service;

import com.example.back_end_java.entity.LoginRequest;
import com.example.back_end_java.entity.User;
import com.example.back_end_java.entity.UserLogin;
import com.example.back_end_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final String admin = "admin";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTAuth jwtAuth;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User creationUser(User user) throws RuntimeException{
          String email = user.getEmail();

          User existedEmail = userRepository.findByEmailAndRole(email , admin);
             if (existedEmail != null){
                 throw new RuntimeException("email already existing");
             }
             String hasPassword = passwordEncoder.encode(user.getPassword());
             user.setPassword(hasPassword);
             user.setRole(admin);

            return  userRepository.save(user);
    }

    public UserLogin loginUser(LoginRequest loginRequest) {
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            User existingEmail = userRepository.findByEmailAndRole(email, admin);

            if (existingEmail == null) {
                throw new RuntimeException("email not exist , try again");
            }
            String mypass = existingEmail.getPassword();
            boolean comparedPass = passwordEncoder.matches(password, mypass);
            if (!comparedPass) {
                throw new RuntimeException("password no match");
            }
            String token = jwtAuth.generateToken(email);
            if (token == null ){
                throw new RuntimeException("not token found");
            }
            UserLogin userLogin = new UserLogin();
            userLogin.setEmail(email);
            userLogin.setToken(token);
            return userLogin;
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User editUser(Long id , User user){
        User getUserById = userRepository.getReferenceById(id);
        String hashPass = passwordEncoder.encode(user.getPassword());
        getUserById.setId(id);
        getUserById.setName(user.getName());
        getUserById.setEmail(user.getEmail());
        getUserById.setPassword(hashPass);
        return userRepository.save(getUserById);
    }

    public void  deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
