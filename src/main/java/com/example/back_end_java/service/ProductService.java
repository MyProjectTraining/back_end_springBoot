package com.example.back_end_java.service;

import com.example.back_end_java.components.ProductDTO;
import com.example.back_end_java.entity.image.Image;
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

    public ProductResponse createProductUser(String email, ProductRequest productRequest, MultipartFile file) throws IOException {
        Image image = new Image();
        User user = userRepository.getByEmail(email);
        Product productExisting = productRepository.getByName(productRequest.getName());
        if (productExisting != null) {
            productExisting.setQuantity(productRequest.getQuantity() + productExisting.getQuantity());
            return productDTO.DTO(productRepository.save(productExisting));
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setUser(user);
        Product saveProduct = productRepository.save(product);
        String nameImage = imageService.saveImage(file);

        image.setFilename(nameImage);
        image.setProduct(saveProduct);
        Image saveImage = imageRepository.save(image);
        saveProduct.setImage(saveImage);

        return productDTO.DTO(saveProduct);
    }

    public ProductResponse deleteProductUser(String email, String nameProduct) throws IOException {
        User user = userRepository.getByEmail(email);
        if (user == null){
            throw new Error("user not found ");
        }
        Product productExisting = productRepository.getByName(nameProduct);
        if (productExisting.getQuantity() > 1){
            productExisting.setQuantity(productExisting.getQuantity() - 1);
            return productDTO.DTO(productRepository.save(productExisting));
        }

        String imageProduct =  productExisting.getImage().getFilename();
        imageService.deleteImage(imageProduct);
        productRepository.delete(productExisting);
        return null;
    }
}
