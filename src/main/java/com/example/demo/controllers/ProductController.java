package com.example.demo.controllers;

import com.example.demo.dto.ProductRecordDTO;
import com.example.demo.exceptions.Exception;
import com.example.demo.models.ProductModel;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        var productModel = new ProductModel();

        // Converter de DTO para MODEL
        BeanUtils.copyProperties(productRecordDTO, productModel);

        //Construindo o retorno..
        // Primeiro o STATUS E DEPOIS OQ PASSOU NO CORPO (NOME, VALOR E UUID)

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productModelList = productService.findAll();
        if(!productModelList.isEmpty()) {
            for (ProductModel product : productModelList) {
                UUID id  = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getById(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(productModelList);
    }


    @GetMapping("/products/{id}")
    public  ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModel = productService.findById(id);

        if(productModel.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found ");

        return ResponseEntity.status(HttpStatus.OK).body(productModel.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO) {

        Optional<ProductModel> productM = productService.findById(id);

        if(productM.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not found");
        }

        var productModel  = productM.get();
        BeanUtils.copyProperties(productRecordDTO, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productM = productService.findById(id);

        if(productM.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not found");
        }

        productService.delete(productM.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");

    }
}
