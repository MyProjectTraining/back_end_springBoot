package com.example.back_end_java.repository;

import com.example.back_end_java.entity.image.Image;
import com.example.back_end_java.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image , Long> {
    @Query("select p from Product p join fetch p.image i where i.id = :id")
    Image getProductsById(@Param("id") Long id);
}
