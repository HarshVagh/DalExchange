package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.dto.ProductRatingAdminDTO;
import com.asdc.dalexchange.dto.ProductRatingDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.mappers.impl.ProductRatingMapperImpl;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.repository.ProductRatingRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductRatingServiceImplTest {

    @Mock
    private ProductRatingRepository productRatingRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRatingMapperImpl productRatingMapper;

    @Mock
    private Mapper<ProductRating, ProductRatingAdminDTO> productRatingAdminMapper;

    @InjectMocks
    private ProductRatingServiceImpl productRatingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllReviewOfAllSoldItemsOfUser() {
        Long userId = 1L;

        User seller = new User();
        seller.setUserId(userId);

        ProductCategory category = new ProductCategory();
        category.setCategoryId(1L);
        category.setName("Electronics");

        Product product1 = new Product();
        product1.setProductId(101L);
        product1.setSeller(seller);
        product1.setTitle("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(100.0);
        product1.setCategory(category);
        product1.setProductCondition(ProductCondition.NEW);
        product1.setUseDuration("1 month");
        product1.setShippingType(ShippingType.FREE);
        product1.setQuantityAvailable(10);
        product1.setCreatedAt(LocalDateTime.now());
        product1.setUnlisted(false);

        Product product2 = new Product();
        product2.setProductId(102L);
        product2.setSeller(seller);
        product2.setTitle("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(200.0);
        product2.setCategory(category);
        product2.setProductCondition(ProductCondition.USED);
        product2.setUseDuration("6 months");
        product2.setShippingType(ShippingType.PAID);
        product2.setQuantityAvailable(5);
        product2.setCreatedAt(LocalDateTime.now());
        product2.setUnlisted(false);

        when(productRepository.findAll(any(Specification.class)))
                .thenReturn(Arrays.asList(product1, product2));

        User buyer = new User();
        buyer.setUserId(2L);

        ProductRating rating1 = new ProductRating();
        rating1.setProduct(product1);
        rating1.setUser(buyer);
        rating1.setReview("Great product");
        rating1.setRating(5);

        ProductRating rating2 = new ProductRating();
        rating2.setProduct(product2);
        rating2.setUser(buyer);
        rating2.setReview("Good product");
        rating2.setRating(4);

        when(productRatingRepository.findByIdProductId(101L))
                .thenReturn(Arrays.asList(rating1));
        when(productRatingRepository.findByIdProductId(102L))
                .thenReturn(Arrays.asList(rating2));

        ProductRatingDTO ratingDTO1 = new ProductRatingDTO();
        ratingDTO1.setRating(5);
        ratingDTO1.setReview("Great product");

        ProductRatingDTO ratingDTO2 = new ProductRatingDTO();
        ratingDTO2.setRating(4);
        ratingDTO2.setReview("Good product");

        when(productRatingMapper.mapTo(rating1)).thenReturn(ratingDTO1);
        when(productRatingMapper.mapTo(rating2)).thenReturn(ratingDTO2);

        List<ProductRatingDTO> result = productRatingService.allReviewOfAllSoldItemsOfUser(userId);

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getRating());
        assertEquals("Great product", result.get(0).getReview());
        assertEquals(4, result.get(1).getRating());
        assertEquals("Good product", result.get(1).getReview());

        verify(productRepository, times(1)).findAll(any(Specification.class));
        verify(productRatingRepository, times(1)).findByIdProductId(101L);
        verify(productRatingRepository, times(1)).findByIdProductId(102L);
        verify(productRatingMapper, times(1)).mapTo(rating1);
        verify(productRatingMapper, times(1)).mapTo(rating2);
    }

    @Test
    void testGetAllReviews() {
        ProductRating rating1 = new ProductRating();
        ProductRating rating2 = new ProductRating();
        List<ProductRating> mockRatings = Arrays.asList(rating1, rating2);

        ProductRatingAdminDTO dto1 = new ProductRatingAdminDTO();
        ProductRatingAdminDTO dto2 = new ProductRatingAdminDTO();

        when(productRatingRepository.findAll()).thenReturn(mockRatings);
        when(productRatingAdminMapper.mapTo(any(ProductRating.class)))
                .thenReturn(dto1)
                .thenReturn(dto2);

        List<ProductRatingAdminDTO> result = productRatingService.getAllReviews();

        assertEquals(2, result.size());
        verify(productRatingRepository, times(1)).findAll();
        verify(productRatingAdminMapper, times(2)).mapTo(any(ProductRating.class));
    }

    @Test
    void testGetAllReviewsByProduct() {
        Long productId = 1L;
        ProductRating rating1 = new ProductRating();
        ProductRating rating2 = new ProductRating();
        List<ProductRating> mockRatings = Arrays.asList(rating1, rating2);

        ProductRatingAdminDTO dto1 = new ProductRatingAdminDTO();
        ProductRatingAdminDTO dto2 = new ProductRatingAdminDTO();

        when(productRatingRepository.findByIdProductId(productId)).thenReturn(mockRatings);
        when(productRatingAdminMapper.mapTo(any(ProductRating.class)))
                .thenReturn(dto1)
                .thenReturn(dto2);

        List<ProductRatingAdminDTO> result = productRatingService.getAllReviewsByProduct(productId);

        assertEquals(2, result.size());
        verify(productRatingRepository, times(1)).findByIdProductId(productId);
        verify(productRatingAdminMapper, times(2)).mapTo(any(ProductRating.class));
    }

    @Test
    void testDeleteReview() {
        Long productId = 1L;
        Long userId = 1L;
        ProductRatingID id = new ProductRatingID(productId, userId);

        productRatingService.deleteReview(productId, userId);

        verify(productRatingRepository, times(1)).deleteById(id);
    }
}



