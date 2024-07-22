package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Product> addProduct(
            @RequestPart("addProductDTO") AddProductDTO addProductDTO,
            @RequestPart("file") MultipartFile file) {
        try {
            ProductCategory category = productService.getCategoryById(addProductDTO.getCategoryId());
            if (category == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Product savedProduct = productService.addProduct(addProductDTO, category, file);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = productService.getProductByID(id);
        if (product != null && product.getImagePath() != null) {
            try {
                Path imagePath = Paths.get(product.getImagePath());
                byte[] image = Files.readAllBytes(imagePath);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
