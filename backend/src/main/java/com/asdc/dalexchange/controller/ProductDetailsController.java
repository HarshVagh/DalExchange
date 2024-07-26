package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.service.ProductDetailsService;
import com.asdc.dalexchange.service.ProductWishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product_details")
@Slf4j
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @GetMapping("")
    public ResponseEntity<ProductDetailsDTO> product(
            @RequestParam(defaultValue = "0") Long productId) {
        log.info("Fetching product details for productId: {}", productId);
        ProductDetailsDTO productDetailsDTO = productDetailsService.getDetails(productId);
        log.info("Fetched product details successfully for productId: {}", productId);
        return ResponseEntity.ok().body(productDetailsDTO);
    }

    @GetMapping("/favorite")
    public ResponseEntity<String> markAsFavorite(
            @RequestParam(defaultValue = "0") Long productId) {
        log.info("Marking product as favorite for productId: {}", productId);
        ProductWishlistDTO productWishlistDTO = new ProductWishlistDTO();
        productWishlistDTO.setProductId(productId);

        productWishlistService.markProductAsFavorite(productId);

        log.info("Product added successfully to wishlist for productId: {}", productId);
        return ResponseEntity.ok().body("Product added Successfully in wishlist.");
    }
}
