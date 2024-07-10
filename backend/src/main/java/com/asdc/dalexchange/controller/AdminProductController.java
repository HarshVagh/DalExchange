package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.service.impl.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductServiceImp productService;

    @Autowired
    public AdminProductController(ProductServiceImp productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/update/{productId}")
    public Product updateProduct(
            @PathVariable Long productId,
            @RequestBody Product updatedProductDetails) {
        return productService.updateProduct(productId, updatedProductDetails);
    }

    @DeleteMapping("/unlist/{productId}")
    public void unlistProduct(@PathVariable Long productId) {
        productService.unlistProduct(productId);
    }
}
