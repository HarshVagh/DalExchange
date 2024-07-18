package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        addProductDTO.setCategoryId(1L);

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

        when(productService.getCategoryById(1L)).thenReturn(category);
        when(productService.addProduct(any(AddProductDTO.class), any(ProductCategory.class), any(MultipartFile.class))).thenReturn(savedProduct);

        ResponseEntity<Product> response = productController.addProduct(addProductDTO, file);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedProduct.getId(), response.getBody().getId());
        verify(productService, times(1)).getCategoryById(1L);
        verify(productService, times(1)).addProduct(any(AddProductDTO.class), any(ProductCategory.class), any(MultipartFile.class));
    }

    @Test
    void addProductTest_CategoryNotFound() throws IOException {
        AddProductDTO addProductDTO = new AddProductDTO();
        addProductDTO.setCategoryId(1L);

        MultipartFile file = mock(MultipartFile.class);

        when(productService.getCategoryById(1L)).thenReturn(null);

        ResponseEntity<Product> response = productController.addProduct(addProductDTO, file);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(productService, times(1)).getCategoryById(1L);
        verify(productService, times(0)).addProduct(any(AddProductDTO.class), any(ProductCategory.class), any(MultipartFile.class));
    }

    @Test
    void addProductTest_IOException() throws IOException {
        AddProductDTO addProductDTO = new AddProductDTO();
        addProductDTO.setCategoryId(1L);

        ProductCategory category = new ProductCategory();
        category.setId(1L);
        category.setName("Test Category");

        MultipartFile file = mock(MultipartFile.class);

        when(productService.getCategoryById(1L)).thenReturn(category);
        when(productService.addProduct(any(AddProductDTO.class), any(ProductCategory.class), any(MultipartFile.class))).thenThrow(IOException.class);

        ResponseEntity<Product> response = productController.addProduct(addProductDTO, file);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(productService, times(1)).getCategoryById(1L);
        verify(productService, times(1)).addProduct(any(AddProductDTO.class), any(ProductCategory.class), any(MultipartFile.class));
    }

    @Test
    void getProductImageTest() {
        Product product = new Product();
        product.setId(1L);
        product.setImagePath("/Users/shivaniuppe/Desktop/dal-exchange/group09/backend/src/uploads/test.jpg");

        when(productService.getProductByID(1L)).thenReturn(product);
        byte[] imageBytes = new byte[]{1, 2, 3, 4};
        Path imagePath = Paths.get(product.getImagePath());

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(imagePath)).thenReturn(imageBytes);

            ResponseEntity<byte[]> response = productController.getProductImage(1L);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
            assertEquals(imageBytes, response.getBody());
            verify(productService, times(1)).getProductByID(1L);
        }
    }

    @Test
    void getProductImageTest_ProductNotFound() {
        when(productService.getProductByID(1L)).thenReturn(null);

        ResponseEntity<byte[]> response = productController.getProductImage(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).getProductByID(1L);
    }

    @Test
    void getProductImageTest_IOException(){
        Product product = new Product();
        product.setId(1L);
        product.setImagePath("/Users/shivaniuppe/Desktop/dal-exchange/group09/backend/src/uploads/test.jpg");

        when(productService.getProductByID(1L)).thenReturn(product);
        Path imagePath = Paths.get(product.getImagePath());

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(imagePath)).thenThrow(IOException.class);

            ResponseEntity<byte[]> response = productController.getProductImage(1L);

            assertNotNull(response);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            verify(productService, times(1)).getProductByID(1L);
        }
    }
}
