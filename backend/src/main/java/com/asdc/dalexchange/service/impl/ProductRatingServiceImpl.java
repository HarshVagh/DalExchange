package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductRatingDTO;
import com.asdc.dalexchange.mappers.impl.ProductRatingMapperImpl;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductRating;
import com.asdc.dalexchange.repository.ProductRatingRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductRatingServiceImpl implements ProductRatingService {

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRatingMapperImpl productRatingMapper;

    @Override
    public List<ProductRatingDTO> allReviewOfAllSoldItemsOfUser(Long userid) {
        List<Product> products = productRepository.findAll(ProductSpecification.bySellerUserId(userid));
        List<Long> productIds = products.stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        List<ProductRating> allProductRatings = productIds.stream()
                .flatMap(productId -> productRatingRepository.findByIdProductId(productId).stream())
                .collect(Collectors.toList());

        return allProductRatings.stream()
                .map(productRatingMapper::mapTo)
                .collect(Collectors.toList());

    }
}