package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.dto.ProductDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Mapper<Product, ProductDTO> productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;


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

    @Test
    void addProductTest() throws IOException {
        AddProductDTO addProductDTO = new AddProductDTO();
        addProductDTO.setTitle("Test Product");
        addProductDTO.setDescription("Test Description");
        addProductDTO.setPrice(100.0);
        addProductDTO.setProductCondition(ProductCondition.NEW);
        addProductDTO.setUseDuration("1 year");
        addProductDTO.setShippingType(ShippingType.FREE);
        addProductDTO.setQuantityAvailable(10);

        ProductCategory category = new ProductCategory();
        category.setId(1L);
        category.setName("Test Category");

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenReturn(new byte[0]);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setTitle(addProductDTO.getTitle());
        savedProduct.setDescription(addProductDTO.getDescription());
        savedProduct.setPrice(addProductDTO.getPrice());
        savedProduct.setCategory(category);
        savedProduct.setProductCondition(addProductDTO.getProductCondition());
        savedProduct.setUseDuration(addProductDTO.getUseDuration());
        savedProduct.setShippingType(addProductDTO.getShippingType());
        savedProduct.setQuantityAvailable(addProductDTO.getQuantityAvailable());
        savedProduct.setImagePath("/Users/shivaniuppe/Desktop/dal-exchange/group09/backend/src/uploads/" + UUID.randomUUID() + "_test.jpg");
        savedProduct.setCreatedAt(LocalDateTime.now());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product product = productService.addProduct(addProductDTO, category, file);

        assertNotNull(product);
        assertEquals(addProductDTO.getTitle(), product.getTitle());
        assertEquals(addProductDTO.getDescription(), product.getDescription());
        assertEquals(addProductDTO.getPrice(), product.getPrice());
        assertEquals(category, product.getCategory());
        assertEquals(addProductDTO.getProductCondition(), product.getProductCondition());
        assertEquals(addProductDTO.getUseDuration(), product.getUseDuration());
        assertEquals(addProductDTO.getShippingType(), product.getShippingType());
        assertEquals(addProductDTO.getQuantityAvailable(), product.getQuantityAvailable());
        assertNotNull(product.getImagePath());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getCategoryByIdTest() {
        ProductCategory category = new ProductCategory();
        category.setId(1L);
        category.setName("Test Category");

        when(productCategoryRepository.findById(1L)).thenReturn(Optional.of(category));

        ProductCategory result = productService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
        verify(productCategoryRepository, times(1)).findById(1L);
    }

    @Test
    void ggetProductByIDTest() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductByID(1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getTitle(), result.getTitle());
        verify(productRepository, times(1)).findById(1L);
    }

}
