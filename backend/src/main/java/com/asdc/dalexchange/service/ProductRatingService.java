package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductRatingDTO;

import java.util.List;

public interface ProductRatingService {
    public List<ProductRatingDTO> allReviewOfAllSoldItemsOfUser();
}
