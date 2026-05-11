package com.example.back_end_java.controller;

import com.example.back_end_java.entity.product.Product;
import com.example.back_end_java.entity.product.ProductRequest;
import com.example.back_end_java.entity.product.ProductResponse;
import com.example.back_end_java.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/productWithImg/user/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(@PathVariable Long id ,
                                      @RequestParam("name") String name,
                                      @RequestParam("price") int price,
                                      @RequestParam("quantity") int quantity,
                                      @RequestParam("image") MultipartFile file) throws IOException {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(name);
        productRequest.setPrice(price);
        productRequest.setQuantity(quantity);
        return productService.createProductUser(id , productRequest , file);
    }
}
