package com.example.back_end_java.service;

import com.example.back_end_java.components.ProductDTO;
import com.example.back_end_java.entity.account.Account;
import com.example.back_end_java.entity.account.Type.AccountType;
import com.example.back_end_java.entity.image.Image;
import com.example.back_end_java.entity.product.Product;
import com.example.back_end_java.entity.product.ProductRequest;
import com.example.back_end_java.entity.product.ProductResponse;
import com.example.back_end_java.entity.user.User;
import com.example.back_end_java.repository.AccountRepository;
import com.example.back_end_java.repository.ImageRepository;
import com.example.back_end_java.repository.ProductRepository;
import com.example.back_end_java.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductDTO productDTO;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final AccountRepository accountRepository;


    public ProductService(UserRepository userRepository, ProductRepository productRepository, ProductDTO productDTO, ImageRepository imageRepository, ImageService imageService, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productDTO = productDTO;
        this.imageRepository = imageRepository;

        this.imageService = imageService;
        this.accountRepository = accountRepository;
    }

    public ProductResponse createProductUser(String email, AccountType accountType, ProductRequest productRequest, MultipartFile file) throws IOException {

        Image image = new Image();
        Account account = accountRepository.getAccountByEmailAndAccountType(email,accountType);
        User user = account.getUser();
        Product productExisting = productRepository.getByName(productRequest.getName());
        if (productExisting != null) {
            productExisting.setPrice(productRequest.getPrice());
            productExisting.setQuantity(productRequest.getQuantity() + productExisting.getQuantity());
            int total = productExisting.getQuantity() * productExisting.getPrice();
            int newline = account.getMoney() - total;
            if (newline < 0){
                System.out.print("no money , your account no money");
                throw new Error("no money");
            }
            account.setMoney(newline);
            accountRepository.save(account);
            return productDTO.DTO(productRepository.save(productExisting));
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setUser(user);
        int newLine = product.getPrice() * product.getQuantity();
        int total = account.getMoney() - newLine;
        account.setMoney(total);
        accountRepository.save(account);
        Product saveProduct = productRepository.save(product);
        String nameImage = imageService.saveImage(file);

        image.setFilename(nameImage);
        image.setProduct(saveProduct);
        Image saveImage = imageRepository.save(image);
        saveProduct.setImage(saveImage);

        return productDTO.DTO(saveProduct);
    }

    public List<ProductResponse> getAllProduct(){
        List<Product> productLists = productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : productLists){
            responses.add(productDTO.DTO(product));
        }

        return responses;
    }

    public List<ProductResponse> getAllProductByEmail(String email){
        List<ProductResponse> productResponses = new ArrayList<>();
        User user = userRepository.getByEmail(email);
        if (user == null){
            throw new Error("no product found for email" + email);
        }
        List<Product> responses = user.getProducts();
        for (Product product: responses){
            productResponses.add(productDTO.DTO(product));
        }
        return productResponses;
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
