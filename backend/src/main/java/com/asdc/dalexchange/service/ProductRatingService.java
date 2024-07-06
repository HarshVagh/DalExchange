package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.ProductRating;

import java.util.List;

public interface ProductRatingService {
    public List<ProductRating> getProductRatingsByUserId(Long userId);
}
