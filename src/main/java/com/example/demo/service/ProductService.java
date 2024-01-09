package com.example.demo.service;

import com.example.demo.models.ProductModel;
import com.example.demo.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@Setter
public class ProductService {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }


    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }


    public Optional<ProductModel> findById(UUID id) {
        return productRepository.findById(id);
    }


    public Optional<ProductModel> updateProduct(UUID id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }

}
