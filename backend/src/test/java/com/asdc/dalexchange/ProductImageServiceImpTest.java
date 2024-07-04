package com.asdc.dalexchange;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.service.imp.ProductImageServiceImp;
import com.asdc.dalexchange.specification.ProductImageSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductImageServiceImpTest {

    @Mock
    private ProductImageRepository productImageRepository;

    @InjectMocks
    private ProductImageServiceImp productImageService;

    private Long productId;
    private List<ProductImage> productImages;

    @BeforeEach
    public void setUp() {
        productId = 1L;

        Product product = new Product();
        product.setProductId(productId);

        ProductImage image1 = new ProductImage();
        image1.setImageId(1L);
        image1.setProduct(product);
        image1.setImageUrl("image1.jpg");

        ProductImage image2 = new ProductImage();
        image2.setImageId(2L);
        image2.setProduct(product);
        image2.setImageUrl("image2.jpg");

        ProductImage image3 = new ProductImage();
        image3.setImageId(3L);
        image3.setProduct(product);
        image3.setImageUrl("image3.jpg");

        productImages = Arrays.asList(image1, image2, image3);
    }

    @Test
    public void testGetProductAllImagesSuccess() {
        // Mock the behavior of ProductImageRepository
        Specification<ProductImage> specification = ProductImageSpecification.byProductId(productId);
        when(productImageRepository.findAll(specification)).thenReturn(productImages);

        // Call the service method under test
        List<String> result = productImageService.getProductAllImages(productId);

        // Assert the result
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"), result);

        // Verify repository method invocation
        verify(productImageRepository, times(1)).findAll(specification);
    }

    @Test
    public void testGetProductAllImagesNoImagesFound() {
        // Mock the behavior of ProductImageRepository
        Specification<ProductImage> specification = ProductImageSpecification.byProductId(productId);
        when(productImageRepository.findAll(specification)).thenReturn(Collections.emptyList());

        // Call the service method under test
        List<String> result = productImageService.getProductAllImages(productId);

        // Assert the result
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify repository method invocation
        verify(productImageRepository, times(1)).findAll(specification);
    }
}
