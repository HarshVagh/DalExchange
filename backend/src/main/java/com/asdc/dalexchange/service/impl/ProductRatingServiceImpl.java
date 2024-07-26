package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductRatingAdminDTO;
import com.asdc.dalexchange.dto.ProductRatingDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.mappers.impl.ProductRatingMapperImpl;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.ProductRatingRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.specifications.ProductSpecification;
import com.asdc.dalexchange.util.AuthUtil;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductRatingServiceImpl implements ProductRatingService {

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRatingMapperImpl productRatingMapper;

    @Autowired
    private Mapper<ProductRating, ProductRatingAdminDTO> productRatingAdminMapper;

    @Override
    public List<ProductRatingDTO> allReviewOfAllSoldItemsOfUser() {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        List<Product> products = productRepository.findAll(ProductSpecification.bySellerUserId(userId));
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

    @Override
    public List<ProductRatingAdminDTO> getAllReviews() {
        return productRatingRepository.findAll().stream()
                .map(productRatingAdminMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRatingAdminDTO> getAllReviewsByProduct(Long productId) {
        return productRatingRepository.findByIdProductId(productId).stream()
                .map(productRatingAdminMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteReview(Long productId, Long userId) {
        ProductRatingID id = new ProductRatingID(productId, userId);
        productRatingRepository.deleteById(id);
    }

    @Override
    public  void  saveRating(Map<String,Object> requestBody){
        Long productId = Long.parseLong(requestBody.get("productId").toString());
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        Integer rating = Integer.parseInt(requestBody.get("rating").toString());
        String review  = requestBody.get("review").toString();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
        ProductRating productRating = new ProductRating();
        productRating.setRating(rating);
        productRating.setReview(review);
        productRating.setProduct(product);
        productRating.setUser(user);
        ProductRatingID productRatingID = new ProductRatingID(productId, userId);
        productRating.setId(productRatingID);
        productRatingRepository.save(productRating);
    }
}