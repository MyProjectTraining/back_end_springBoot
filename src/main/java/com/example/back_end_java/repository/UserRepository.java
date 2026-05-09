package com.example.back_end_java.repository;

import com.example.back_end_java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    User findByEmailAndRole(String email , String role);
    @Query("SELECT u from User u join fetch u.products p")
    List<User> getALlUserAndProduct();

}
