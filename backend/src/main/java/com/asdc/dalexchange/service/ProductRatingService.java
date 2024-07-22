package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductRatingAdminDTO;
import com.asdc.dalexchange.dto.ProductRatingDTO;

import java.util.List;

public interface ProductRatingService {
    List<ProductRatingDTO> allReviewOfAllSoldItemsOfUser();
    List<ProductRatingAdminDTO> getAllReviewsByProduct(Long productId);
    void deleteReview(Long productId, Long userId);
    List<ProductRatingAdminDTO> getAllReviews();
}
