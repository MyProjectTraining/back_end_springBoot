package com.example.back_end_java.repository;

import com.example.back_end_java.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
    Product getByName(String name);
}
