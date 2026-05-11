package com.example.back_end_java.service;

import com.example.back_end_java.components.ProductDTO;
import com.example.back_end_java.entity.Image;
import com.example.back_end_java.entity.product.Product;
import com.example.back_end_java.entity.product.ProductRequest;
import com.example.back_end_java.entity.product.ProductResponse;
import com.example.back_end_java.entity.user.User;
import com.example.back_end_java.repository.ImageRepository;
import com.example.back_end_java.repository.ProductRepository;
import com.example.back_end_java.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductDTO productDTO;
    private final ImageRepository imageRepository;
    private final ImageService imageService;


    public ProductService(UserRepository userRepository, ProductRepository productRepository, ProductDTO productDTO, ImageRepository imageRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productDTO = productDTO;
        this.imageRepository = imageRepository;

        this.imageService = imageService;
    }

    public ProductResponse createProductUser(Long id , ProductRequest productRequest , MultipartFile file) throws IOException {
        Image image = new Image();
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
        Product saveProduct = productRepository.save(product);
        String url = imageService.saveImage(file);

        image.setFilename(file.getOriginalFilename());
        image.setUrl(url);
        image.setProduct(saveProduct);
        Image saveImage = imageRepository.save(image);
        saveProduct.setImage(saveImage);

        return productDTO.DTO(saveProduct);
    }
}
