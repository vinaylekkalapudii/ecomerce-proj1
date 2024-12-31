package com.vinay.ecomerce_proj1.service;

import com.vinay.ecomerce_proj1.model.Product;
import com.vinay.ecomerce_proj1.repo.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service

public class ProductService {
    private ProductRepo repo;
    public ProductService(ProductRepo repo){

        this.repo=repo;
    }

    public List<Product> getProducts() {

        return repo.findAll();
    }

    public Product getProductsById(int prodId) {

        return repo.findById(prodId).orElseThrow( () -> new RuntimeException("invalid id "));
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
             return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
          repo.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {
        return repo.searchProducts(keyword);
    }
}
