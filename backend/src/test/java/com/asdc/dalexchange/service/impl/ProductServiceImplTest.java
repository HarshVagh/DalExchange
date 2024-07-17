package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.repository.ProductCategoryRepository;
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

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Mapper<Product, ProductDTO> productMapper;

    @Mock
    private Mapper<Product, ProductModerationDTO> productModerationMapper;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

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
        Product product = new Product();
        product.setProductId(1L);
        product.setTitle("Test Product");

        ProductModerationDTO productModerationDTO = new ProductModerationDTO();
        productModerationDTO.setProductId(1L);
        productModerationDTO.setTitle("Test Product DTO");

        List<Product> products = Arrays.asList(product);

        when(productRepository.findAll()).thenReturn(products);
        when(productModerationMapper.mapTo(any(Product.class))).thenReturn(productModerationDTO);

        List<ProductModerationDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(productModerationDTO.getTitle(), result.get(0).getTitle());
        verify(productRepository, times(1)).findAll();
        verify(productModerationMapper, times(1)).mapTo(any(Product.class));
    }

    @Test
    void testUpdateProduct_AllFieldsPresent() {
        Product product = new Product();
        product.setProductId(1L);
        product.setTitle("Old Title");

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(1L);
        productCategory.setName("Test Category");

        ProductModerationDTO productModerationDTO = new ProductModerationDTO();
        productModerationDTO.setProductId(1L);
        productModerationDTO.setTitle("Updated Title");
        productModerationDTO.setDescription("Updated Description");
        productModerationDTO.setPrice(100.0);
        productModerationDTO.setCategory(productCategory);
        productModerationDTO.setProductCondition(ProductCondition.NEW);
        productModerationDTO.setUseDuration("Updated Duration");
        productModerationDTO.setShippingType(ShippingType.free);
        productModerationDTO.setQuantityAvailable(10);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productCategoryRepository.findById(anyLong())).thenReturn(Optional.of(productCategory));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productModerationMapper.mapTo(any(Product.class))).thenReturn(productModerationDTO);

        ProductModerationDTO updatedProduct = productService.updateProduct(1L, productModerationDTO);

        assertEquals("Updated Title", updatedProduct.getTitle());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productCategoryRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productModerationMapper, times(1)).mapTo(any(Product.class));
    }

    @Test
    void testUpdateProduct_MissingFields() {
        Product product = new Product();
        product.setProductId(1L);
        product.setTitle("Old Title");

        ProductModerationDTO productModerationDTO = new ProductModerationDTO();
        productModerationDTO.setProductId(1L);
        productModerationDTO.setTitle("Updated Title");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productModerationMapper.mapTo(any(Product.class))).thenReturn(productModerationDTO);

        ProductModerationDTO updatedProduct = productService.updateProduct(1L, productModerationDTO);

        assertEquals("Updated Title", updatedProduct.getTitle());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productModerationMapper, times(1)).mapTo(any(Product.class));
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        ProductModerationDTO productModerationDTO = new ProductModerationDTO();
        productModerationDTO.setProductId(1L);
        productModerationDTO.setTitle("Updated Product");

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1L, productModerationDTO);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateProduct_CategoryNotFound() {
        Product product = new Product();
        product.setProductId(1L);
        product.setTitle("Test Product");

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(1L);

        ProductModerationDTO productModerationDTO = new ProductModerationDTO();
        productModerationDTO.setProductId(1L);
        productModerationDTO.setTitle("Updated Product");
        productModerationDTO.setCategory(productCategory);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1L, productModerationDTO);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productCategoryRepository, times(1)).findById(anyLong());
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
