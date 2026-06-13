package com.example.back_end_java.controller;

import com.example.back_end_java.entity.account.Type.AccountType;
import com.example.back_end_java.entity.product.Product;
import com.example.back_end_java.entity.product.ProductRequest;
import com.example.back_end_java.entity.product.ProductResponse;
import com.example.back_end_java.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/productWithImg/user/{email}/accountType/{accountType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(@PathVariable String email ,
                                      @PathVariable AccountType accountType,
                                      @RequestParam("name") String name,
                                      @RequestParam("price") int price,
                                      @RequestParam("quantity") int quantity,
                                      @RequestParam("image") MultipartFile file) throws IOException {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(name);
        productRequest.setPrice(price);
        productRequest.setQuantity(quantity);
        return productService.createProductUser(email , accountType, productRequest , file);
    }

    @GetMapping("/productWithImg")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProduct();
    }

    @GetMapping("/productWithImg/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProductResponse> getAllProductByEmail(@PathVariable String email){
        return productService.getAllProductByEmail(email);
    }

    @DeleteMapping("/productWithImg/user/{email}/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse deleteProduct(@PathVariable String email , @PathVariable String productName) throws IOException {
        return productService.deleteProductUser(email , productName);
    }

    @PutMapping("/productWithImg/{id}/user/{email}/{productName}/accountType/{accountType}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable String email ,@PathVariable Long id , @PathVariable AccountType accountType,
                                         @RequestParam("name") String name,
                                         @RequestParam("price") int price ,
                                         @RequestParam("quantity") int quantity,
                                         @RequestParam("image") MultipartFile file) throws IOException {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(name);
        productRequest.setQuantity(quantity);
        productRequest.setPrice(price);
        return productService.updateProduct(email,id,accountType,productRequest,file);
    }
}
