package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId(1L);
        product.setTitle("Old Title");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = new Product();
        updatedProduct.setTitle("New Title");
        updatedProduct.setDescription("New Description");
        updatedProduct.setPrice(100.0);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals(100.0, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1L, new Product());
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUnlistProduct() {
        Product product = new Product();
        product.setProductId(1L);
        product.setUnlisted(false);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.unlistProduct(1L);

        assertTrue(product.isUnlisted());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUnlistProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.unlistProduct(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

}
