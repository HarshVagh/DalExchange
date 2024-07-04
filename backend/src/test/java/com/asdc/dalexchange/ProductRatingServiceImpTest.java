package com.asdc.dalexchange;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.asdc.dalexchange.model.ProductRating;
import com.asdc.dalexchange.repository.ProductRatingRepository;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.service.imp.ProductRatingServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductRatingServiceImpTest {

    @Mock
    private ProductRatingRepository productRatingRepository;

    @InjectMocks
    private ProductRatingServiceImp productRatingService;

    private Long userId;
    private List<ProductRating> productRatings;

    @BeforeEach
    public void setUp() {
        userId = 1L;
        ProductRating rating1 = new ProductRating();
        rating1.setRating(4);
        rating1.setReview("Good product!");

        ProductRating rating2 = new ProductRating();
        rating2.setRating(5);
        rating2.setReview("Excellent!");

        productRatings = Arrays.asList(rating1, rating2);
    }

    @Test
    public void testGetProductRatingsByUserId() {
        // Mock behavior of productRatingRepository
        when(productRatingRepository.findByIdUserId(userId)).thenReturn(productRatings);

        // Call the method under test
        List<ProductRating> result = productRatingService.getProductRatingsByUserId(userId);

        // Assertions to verify correctness of result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(4, result.get(0).getRating());
        assertEquals("Good product!", result.get(0).getReview());
        assertEquals(5, result.get(1).getRating());
        assertEquals("Excellent!", result.get(1).getReview());

        // Verify that mocked method was called exactly once
        verify(productRatingRepository, times(1)).findByIdUserId(userId);
    }
}

