package com.example.back_end_java.entity.product;

import com.example.back_end_java.entity.Type.Devise;
import lombok.*;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
    private String name;
    private int price;
    private int quantity;
    private Devise devise;
}
