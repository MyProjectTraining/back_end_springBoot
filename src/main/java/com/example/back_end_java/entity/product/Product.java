package com.example.back_end_java.entity.product;

import com.example.back_end_java.entity.Image;
import com.example.back_end_java.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
    @Min(value = 1, message = "Price must be greater than 0")
    private int price;
    @OneToOne(mappedBy = "product" , cascade = CascadeType.ALL)
    private Image image;
}
