package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.ProductRatingAdminDTO;
import com.asdc.dalexchange.service.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reviews")
public class AdminReviewController {
    @Autowired
    private ProductRatingService productRatingService;

    @GetMapping("/all")
    public List<ProductRatingAdminDTO> getAllReviews() {
        return productRatingService.getAllReviews();
    }

    @GetMapping("/product/{productId}")
    public List<ProductRatingAdminDTO> getAllReviewsByProduct(@PathVariable Long productId) {
        return productRatingService.getAllReviewsByProduct(productId);
    }

    @DeleteMapping("/delete")
    public void deleteReview(@RequestParam Long productId, @RequestParam Long userId) {
        productRatingService.deleteReview(productId, userId);
    }
}
