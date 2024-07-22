package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class ProductDetailsServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private ProductWishlistRepository productWishlistRepository;

    @Mock
    private Mapper<Product, ProductDetailsDTO> productDetailsMapper;

    @InjectMocks
    private ProductDetailsServiceImpl productDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDetailsProductNotFound() {
        // Setup
        Long userId = 1L;
        Long productId = 1L;

        // Mocking
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Execute & Verify
        assertThrows(ResourceNotFoundException.class, () -> productDetailsService.getDetails(productId));

        // Verify interactions
        verify(productRepository).findById(productId);
        verifyNoMoreInteractions(productImageRepository, productWishlistRepository, productDetailsMapper);
    }

    @Test
    void testGetDetailsSuccess() {
        // Setup
        Long userId = 1L;
        Long productId = 1L;
        Product product = new Product();
        User seller = new User();
        seller.setUserId(2L);
        seller.setJoinedAt(LocalDateTime.now());
        seller.setSellerRating(4.5);
        product.setSeller(seller);

        ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
        productDetailsDTO.setCategory("Books");
        productDetailsDTO.setSellerId(seller.getUserId());
        productDetailsDTO.setSellerJoiningDate(seller.getJoinedAt());
        productDetailsDTO.setRating(seller.getSellerRating());
        productDetailsDTO.setImageUrls(List.of("url1", "url2"));
        productDetailsDTO.setFavorite(true);

        ProductImage productImage1 = new ProductImage();
        productImage1.setProduct(product);
        productImage1.setImageUrl("url1");

        ProductImage productImage2 = new ProductImage();
        productImage2.setProduct(product);
        productImage2.setImageUrl("url2");

        List<ProductImage> productImages = List.of(productImage1, productImage2);

        // Mocking
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productImageRepository.findAll(any(Specification.class))).thenReturn(productImages);
        when(productDetailsMapper.mapTo(product)).thenReturn(productDetailsDTO);
        when(productWishlistRepository.count(any(Specification.class))).thenReturn(1L);

        // Execute
        ProductDetailsDTO result = productDetailsService.getDetails(productId);

        // Verify
        assertNotNull(result);
        assertEquals("Books", result.getCategory());
        assertEquals(2L, result.getSellerId());
        assertEquals(seller.getJoinedAt(), result.getSellerJoiningDate());
        assertEquals(4.5, result.getRating());
        assertEquals(List.of("url1", "url2"), result.getImageUrls());
        assertTrue(result.isFavorite());

        // Verify interactions
        verify(productRepository).findById(productId);
        verify(productImageRepository).findAll(any(Specification.class));
        verify(productDetailsMapper).mapTo(product);
        verify(productWishlistRepository).count(any(Specification.class));
    }


    @Test
    void testGetProductById() {
        // Setup
        Long productId = 1L;
        Product product = new Product();

        // Mocking
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Execute
        Product result = productDetailsService.getProductById(productId);

        // Verify
        assertNotNull(result);
        assertEquals(product, result);

        // Verify interactions
        verify(productRepository).findById(productId);
    }

    @Test
    void testGetProductByIdNotFound() {
        // Setup
        Long productId = 1L;

        // Mocking
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Execute & Verify
        assertThrows(ResourceNotFoundException.class, () -> productDetailsService.getProductById(productId));

        // Verify interactions
        verify(productRepository).findById(productId);
    }

    @Test
    void testGetImageUrls() {
        // Setup
        Long productId = 1L;
        ProductImage productImage1 = new ProductImage();
        productImage1.setImageUrl("url1");
        ProductImage productImage2 = new ProductImage();
        productImage2.setImageUrl("url2");
        List<ProductImage> productImages = List.of(productImage1, productImage2);

        // Mocking
        when(productImageRepository.findAll(any(Specification.class))).thenReturn(productImages);

        // Execute
        List<String> result = productDetailsService.getImageUrls(productId);

        // Verify
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("url1", result.get(0));
        assertEquals("url2", result.get(1));

        // Verify interactions
        verify(productImageRepository).findAll(any(Specification.class));
    }

    @Test
    void testGetFavoriteStatus() {
        // Setup
        Long userId = 1L;
        Long productId = 1L;

        // Mocking
        when(productWishlistRepository.count(any(Specification.class))).thenReturn(1L);

        // Execute
        boolean result = productDetailsService.getFavoriteStatus(userId, productId);

        // Verify
        assertTrue(result);

        // Verify interactions
        verify(productWishlistRepository).count(any(Specification.class));
    }

    @Test
    void testGetFavoriteStatusNotFavorite() {
        // Setup
        Long userId = 1L;
        Long productId = 1L;

        // Mocking
        when(productWishlistRepository.count(any(Specification.class))).thenReturn(0L);

        // Execute
        boolean result = productDetailsService.getFavoriteStatus(userId, productId);

        // Verify
        assertFalse(result);

        // Verify interactions
        verify(productWishlistRepository).count(any(Specification.class));
    }
}