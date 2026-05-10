package com.example.back_end_java.components;

import com.example.back_end_java.entity.product.Product;
import com.example.back_end_java.entity.product.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductDTO {
    public ProductResponse DTO(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        if(product.getUser() != null){
            productResponse.setEmail(product.getUser().getEmail());
            productResponse.setUsername(product.getUser().getName());
        }
        return productResponse;
    }
}
