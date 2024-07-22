package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductRatingAdminDTO;
import com.asdc.dalexchange.dto.ProductRatingDTO;
import com.asdc.dalexchange.mappers.impl.ProductRatingMapperImpl;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductRating;
import com.asdc.dalexchange.model.ProductRatingID;
import com.asdc.dalexchange.repository.ProductRatingRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.specifications.ProductSpecification;
import com.asdc.dalexchange.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductRatingServiceImplTest {

    @Mock
    private ProductRatingRepository productRatingRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRatingMapperImpl productRatingMapper;

    @InjectMocks
    private ProductRatingServiceImpl productRatingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAllReviewOfAllSoldItemsOfUser_NoReviews() {
        Long userId = 1L;

        // Mock static AuthUtil
        try (MockedStatic<AuthUtil> authUtilMock = mockStatic(AuthUtil.class)) {
            authUtilMock.when(() -> AuthUtil.getCurrentUserId(userRepository)).thenReturn(userId);

            // Mock data
            Product product1 = new Product();
            product1.setProductId(101L);
            Product product2 = new Product();
            product2.setProductId(102L);

            when(productRepository.findAll(ProductSpecification.bySellerUserId(userId)))
                    .thenReturn(Arrays.asList(product1, product2));
            when(productRatingRepository.findByIdProductId(101L)).thenReturn(Collections.emptyList());
            when(productRatingRepository.findByIdProductId(102L)).thenReturn(Collections.emptyList());

            // Call the service method
            List<ProductRatingDTO> result = productRatingService.allReviewOfAllSoldItemsOfUser();

            // Assertions
            assertEquals(0, result.size());
        }
    }



    @Test
    @Transactional
    void testDeleteReview_Exists() {
        Long productId = 1L;
        Long userId = 2L;
        ProductRatingID id = new ProductRatingID(productId, userId);

        doNothing().when(productRatingRepository).deleteById(id);

        productRatingService.deleteReview(productId, userId);

        verify(productRatingRepository, times(1)).deleteById(id);
    }

}