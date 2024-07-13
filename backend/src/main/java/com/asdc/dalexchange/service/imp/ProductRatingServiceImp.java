package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.model.ProductRating;
import com.asdc.dalexchange.repository.ProductRatingRepository;
import com.asdc.dalexchange.service.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductRatingServiceImp implements ProductRatingService {

    /*@Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public List<ProductRating> getProductRatingsByUserId(Long userId) {
        return productRatingRepository.findByIdUserId(userId);
    }*/
}