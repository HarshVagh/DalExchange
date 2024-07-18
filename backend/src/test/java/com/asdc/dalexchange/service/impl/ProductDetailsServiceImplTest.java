package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductDetailsServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private Mapper<Product, ProductDTO> productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

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
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

        // Execute & Verify
        assertThrows(ResourceNotFoundException.class, () -> productDetailsService.getDetails(userId, productId));

        // Verify interactions
        verify(productRepository).findById(productId);
        verifyNoMoreInteractions(productImageRepository, productWishlistRepository, productDetailsMapper);
    }

    // Helper method to create ProductImage instances
    private ProductImage createProductImage(Product product, String imageUrl) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setImageUrl(imageUrl);
        return productImage;
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
//    @Test
//    void testUpdateProduct() {
//        // Setup
//        Long productId = 1L;
//        Product existingProduct = new Product();
//        existingProduct.setProductId(productId);
//
//        User newSeller = new User();
//        newSeller.setUserId(2L);
//        Product updatedProductDetails = new Product();
//        updatedProductDetails.setSeller(newSeller);
//        updatedProductDetails.setTitle("New Title");
//        updatedProductDetails.setDescription("New Description");
//        updatedProductDetails.setPrice(99.99);
//        ProductCategory newCategory = new ProductCategory();
//        updatedProductDetails.setCategory(newCategory);
//        updatedProductDetails.setProductCondition(ProductCondition.NEW);
//        updatedProductDetails.setUseDuration("1 month");
//        updatedProductDetails.setShippingType(ShippingType.PAID);
//        updatedProductDetails.setQuantityAvailable(5);
//
//        // Mocking
//        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
//        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Execute
//        Product result = productService.updateProduct(productId, updatedProductDetails);
//
//        // Verify
//        assertNotNull(result);
//        assertEquals(newSeller, result.getSeller());
//        assertEquals("New Title", result.getTitle());
//        assertEquals("New Description", result.getDescription());
//        assertEquals(99.99, result.getPrice());
//        assertEquals(newCategory, result.getCategory());
//        assertEquals(ProductCondition.NEW, result.getProductCondition());
//        assertEquals("1 month", result.getUseDuration());
//        assertEquals(ShippingType.PAID, result.getShippingType());
//        assertEquals(5, result.getQuantityAvailable());
//
//        // Verify interactions
//        verify(productRepository).findById(productId);
//        verify(productRepository).save(existingProduct);
//    }

//    @Test
//    void testUpdateProductNotFound() {
//        // Setup
//        Long productId = 1L;
//        Product updatedProductDetails = new Product();
//
//        // Mocking
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        // Execute & Verify
//        assertThrows(RuntimeException.class, () -> productService.updateProduct(productId, updatedProductDetails));
//
//        // Verify interactions
//        verify(productRepository).findById(productId);
//    }

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
