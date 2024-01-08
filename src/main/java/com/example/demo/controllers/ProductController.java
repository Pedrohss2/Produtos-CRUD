package com.example.demo.controllers;

import com.example.demo.dto.ProductRecordDTO;
import com.example.demo.models.ProductModel;
import com.example.demo.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;


    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        var productModel = new ProductModel();

        // Converter de DTO para MODEL
        BeanUtils.copyProperties(productRecordDTO, productModel);

        //Construindo o retorno..
        // Primeiro o STATUS E DEPOIS OQ PASSOU NO CORPO (NOME, VALOR E UUID)

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

}
