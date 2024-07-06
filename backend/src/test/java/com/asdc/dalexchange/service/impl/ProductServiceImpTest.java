package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.mapper.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.imp.ProductServiceImp;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Mapper<Product, ProductDTO> productMapper;

    @InjectMocks
    private ProductServiceImp productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById_Exists() {
        // Mock data
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setProductId(productId);
        ProductDTO mockProductDTO = new ProductDTO();
        mockProductDTO.setProductId(productId);

        // Mock repository behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(productMapper.mapTo(mockProduct)).thenReturn(mockProductDTO);

        // Call method under test
        ProductDTO result = productService.getProductById(productId);

        // Assertions
        assertEquals(productId, result.getProductId());
    }

    @Test
    public void testGetProductById_NotFound() {
        // Mock data
        Long productId = 2L;

        // Mock repository behavior
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Call method under test and assert exception
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }
}
