package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Product> addProduct(
            @RequestPart("addProductDTO") AddProductDTO addProductDTO,
            @RequestPart("files") List<MultipartFile> imageFiles) {
        ProductCategory category = productService.getCategoryById(addProductDTO.getCategoryId());
        if (category == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Product savedProduct = productService.addProduct(addProductDTO, category, imageFiles);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}
