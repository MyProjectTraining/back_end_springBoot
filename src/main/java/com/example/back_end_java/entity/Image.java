package com.example.back_end_java.entity;

import com.example.back_end_java.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String filename;
    private String url;
    @OneToOne
    @JoinColumn(name = "img_id")
    private Product product;
}
