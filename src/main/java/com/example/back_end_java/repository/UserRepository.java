package com.example.back_end_java.repository;

import com.example.back_end_java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    User findByEmailAndRole(String email , String role);
}
