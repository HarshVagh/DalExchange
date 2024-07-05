package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.service.imp.ProductImageServiceImp;
import com.asdc.dalexchange.specification.ProductImageSpecification;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductImageServiceImpTest {

    @Mock
    private ProductImageRepository productImageRepository;

    @InjectMocks
    private ProductImageServiceImp productImageService;

    public ProductImageServiceImpTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductAllImages() {
        // Mock data
        Long productId = 1L;
        List<ProductImage> mockProductImages = createMockProductImages(productId);
        List<String> expectedImageUrls = createExpectedImageUrls(mockProductImages);

        // Mock ProductImageSpecification
        Specification<ProductImage> spec = ProductImageSpecification.byProductId(productId);

        // Mock ProductImageRepository behavior
        when(productImageRepository.findAll(spec)).thenReturn(mockProductImages);

        // Call the method under test
        List<String> resultImageUrls = productImageService.getProductAllImages(productId);

        // Assertions
        assertEquals(expectedImageUrls, resultImageUrls);
    }

    // Helper method to create mock ProductImages
    private List<ProductImage> createMockProductImages(Long productId) {
        List<ProductImage> productImages = new ArrayList<>();
        // Add mock product images
        // Adjust as per your test case requirements
        return productImages;
    }

    // Helper method to create expected image URLs
    private List<String> createExpectedImageUrls(List<ProductImage> productImages) {
        List<String> imageUrls = new ArrayList<>();
        // Extract image URLs from product images
        // Adjust as per your test case requirements
        return imageUrls;
    }
}
