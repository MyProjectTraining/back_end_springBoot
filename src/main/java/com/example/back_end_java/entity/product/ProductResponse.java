package com.example.back_end_java.entity.product;

import com.example.back_end_java.entity.image.ImageResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String username;
    private String email;
    private ImageResponse image;
}
