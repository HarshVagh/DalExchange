package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductRatingAdminDTO;
import com.asdc.dalexchange.dto.ProductRatingDTO;
import com.asdc.dalexchange.model.ProductRating;

import java.util.List;

public interface ProductRatingService {
    public List<ProductRatingDTO> allReviewOfAllSoldItemsOfUser(Long userid);
    List<ProductRatingAdminDTO> getAllReviewsByProduct(Long productId);
    void deleteReview(Long productId, Long userId);
    List<ProductRatingAdminDTO> getAllReviews();
}
