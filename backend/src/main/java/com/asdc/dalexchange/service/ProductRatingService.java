package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductRatingDTO;
import com.asdc.dalexchange.model.ProductRating;

import java.util.List;

public interface ProductRatingService {
    //public List<ProductRating> getProductRatingsByUserId(Long userId);
    public List<ProductRatingDTO> GetAllProductRating(Long userid);
}
