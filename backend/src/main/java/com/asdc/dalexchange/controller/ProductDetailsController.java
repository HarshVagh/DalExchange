package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_details")
public class ProductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @GetMapping("")
    public ResponseEntity<ProductDetailsDTO> product(
            @RequestParam(defaultValue = "0") Long productId) {
        long userId = 1L;
        ProductDetailsDTO productDetailsDTO = productDetailsService.getDetails(userId, productId);
        // int sellerId = productDTO.getSellerId();
        return ResponseEntity.ok().body(productDetailsDTO);
    }


    @GetMapping("/favorite")
    public ResponseEntity<String> markAsFavorite(
            @RequestParam(defaultValue = "0") Long productId) {

        long userId = 1L;
        ProductWishlistDTO productWishlistDTO = new ProductWishlistDTO();
        productWishlistDTO.setUserId(userId);
        productWishlistDTO.setProductId(productId);

        productWishlistService.markProductAsFavorite(userId, productId);

        return ResponseEntity.ok().body("Product added Sucessfully in wishlist.");
    }
}
