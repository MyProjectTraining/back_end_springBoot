package com.example.back_end_java.repository;

import com.example.back_end_java.entity.product.Product;
import com.example.back_end_java.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    User getByEmail(String email);
    User findByEmailAndRole(String email , String role);

}
