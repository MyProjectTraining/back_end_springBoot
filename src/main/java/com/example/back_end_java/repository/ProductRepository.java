package com.example.back_end_java.repository;

import com.example.back_end_java.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product , Long> {
    Product getByName(String name);
    @Query("select p from Product p join fetch p.user u where u.email = :email")
    Product getProductsByEmail(@Param("email") String email);
}
