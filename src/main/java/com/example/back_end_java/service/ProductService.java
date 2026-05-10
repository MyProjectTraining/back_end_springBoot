package com.example.back_end_java.service;

import com.example.back_end_java.components.ProductDTO;
import com.example.back_end_java.entity.product.Product;
import com.example.back_end_java.entity.product.ProductRequest;
import com.example.back_end_java.entity.product.ProductResponse;
import com.example.back_end_java.entity.user.User;
import com.example.back_end_java.repository.ProductRepository;
import com.example.back_end_java.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductDTO productDTO;

    public ProductService(UserRepository userRepository, ProductRepository productRepository, ProductDTO productDTO) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productDTO = productDTO;
    }

    public ProductResponse createProductUser(Long id , ProductRequest productRequest){
        User user = userRepository.getReferenceById(id);
        Product productExisting = productRepository.getByName(productRequest.getName());
        if (productExisting != null){
            productExisting.setQuantity(productRequest.getQuantity() + productExisting.getQuantity());
            return productDTO.DTO(productRepository.save(productExisting));
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setUser(user);
        return productDTO.DTO(productRepository.save(product));
    }
}
