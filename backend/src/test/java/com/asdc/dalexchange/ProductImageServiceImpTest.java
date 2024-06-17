package com.asdc.dalexchange;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.service.imp.ProductImageServiceImp;
import com.asdc.dalexchange.service.imp.ProductServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductImageServiceImpTest {

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductImageServiceImp productImageService;

    private Long productId;
    private List<String> imageUrls;

    @BeforeEach
    public void setUp() {
        productId = 1L;
        imageUrls = Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg");
    }

    @Test
    public void testGetProductAllImagesSuccess() {
        when(productImageRepository.findImageUrlsByProductIdWithMultipleImages(productId)).thenReturn(imageUrls);

        List<String> result = productImageService.getProductAllImages(productId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(imageUrls, result);
        verify(productImageRepository, times(1)).findImageUrlsByProductIdWithMultipleImages(productId);
    }

    @Test
    public void testGetProductAllImagesNoImagesFound() {
        when(productImageRepository.findImageUrlsByProductIdWithMultipleImages(productId)).thenReturn(Collections.emptyList());

        List<String> result = productImageService.getProductAllImages(productId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productImageRepository, times(1)).findImageUrlsByProductIdWithMultipleImages(productId);
    }
}
