package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.dto.ProductRatingDTO;
import com.asdc.dalexchange.mappers.impl.ProductRatingMapperImpl;
import com.asdc.dalexchange.model.ProductRating;
import com.asdc.dalexchange.repository.ProductRatingRepository;
import com.asdc.dalexchange.service.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductRatingServiceImp implements ProductRatingService {

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Autowired
    private ProductRatingMapperImpl productRatingMapper;

   /* @Override
    public List<ProductRating> getProductRatingsByUserId(Long userId) {
        return productRatingRepository.findByIdUserId(userId);
    }*/

    @Override
    public List<ProductRatingDTO> GetAllProductRating(Long userid) {
        List<ProductRating> allProductRatings = productRatingRepository.findByIdUserId(userid);
        return allProductRatings.stream()
                .map(productRatingMapper::mapTo)
                .collect(Collectors.toList());
    }
}