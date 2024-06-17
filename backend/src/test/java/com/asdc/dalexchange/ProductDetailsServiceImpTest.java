package com.asdc.dalexchange;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.model.Seller;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.service.imp.ProductDetailsServiceImp;
import com.asdc.dalexchange.service.ProductImageService;
import com.asdc.dalexchange.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductDetailsServiceImpTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductImageService productImageService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductDetailsServiceImp productDetailsService;

    private Long productId;
    private ProductDTO productDTO;
    private ProductDetailsDTO productDetailsDTO;
    private List<String> imageUrls;
    private Seller seller;
    private ProductCategory category;

    @BeforeEach
    public void setUp() {
        productId = 1L;

        // Mock Seller with joining date
        seller = new Seller();
        seller.setUser(new User()); // Assuming Seller has a User object
        seller.getUser().setJoinedAt(LocalDateTime.now());

        // Mock ProductCategory
        category = new ProductCategory();
        category.setName("Electronics");

        // Mock ProductDTO
        productDTO = new ProductDTO();
        productDTO.setSeller(seller.getUser()); // Set the User for the Seller
        productDTO.setCategory(category);

        // Mock image URLs
        imageUrls = Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg");

        // Mock ProductDetailsDTO
        productDetailsDTO = new ProductDetailsDTO();
    }

    @Test
    public void testDetailsOfProductSuccess() {
        // Mock behavior of productService, productImageService, and modelMapper
        when(productService.getProductById(productId)).thenReturn(productDTO);
        when(productImageService.getProductAllImages(productId)).thenReturn(imageUrls);
        when(modelMapper.map(productDTO, ProductDetailsDTO.class)).thenReturn(productDetailsDTO);

        // Call the method under test
        ProductDetailsDTO result = productDetailsService.DetailsOfProduct(productId);

        // Assertions to verify correctness of result
        assertNotNull(result);
        assertEquals(imageUrls, result.getImageurl());
        assertEquals(seller.getUser().getJoinedAt(), result.getSellerJoiningDate());
        assertEquals(category.getName(), result.getCategory());

        // Verify that mocked methods were called exactly once
        verify(productService, times(1)).getProductById(productId);
        verify(productImageService, times(1)).getProductAllImages(productId);
        verify(modelMapper, times(1)).map(productDTO, ProductDetailsDTO.class);

        // Additional assertion to cover setCategory in ProductDetailsDTO
        assertEquals(productDTO.getCategory().getName(), result.getCategory());
    }
}
