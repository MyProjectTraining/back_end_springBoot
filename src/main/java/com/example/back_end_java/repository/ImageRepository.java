package com.example.back_end_java.repository;

import com.example.back_end_java.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image , Long> {
}
