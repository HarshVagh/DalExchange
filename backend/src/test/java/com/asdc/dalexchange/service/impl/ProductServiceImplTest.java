package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.repository.ProductCategoryRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.util.CloudinaryUtil;
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

    @Mock
    private Mapper<Product, ProductModerationDTO> productModerationMapper;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CloudinaryUtil cloudinaryUtil;

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
    void testSetProductListingStatus_Unlist() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.unlistProduct(1L, true);

        assertTrue(product.isUnlisted());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testSetProductListingStatus_List() {
        Product product = new Product();

        product.setUnlisted(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.unlistProduct(1L, false);

        assertFalse(product.isUnlisted());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testSetProductListingStatus_ProductNotFound() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.unlistProduct(1L, true);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
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

        String mockImageUrl = "http://mock.cloudinary.url/test.jpg";
        when(cloudinaryUtil.uploadImage(any(MultipartFile.class))).thenReturn(mockImageUrl);

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
        savedProduct.setImagePath(mockImageUrl);
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
        assertEquals(mockImageUrl, product.getImagePath());
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
    void getProductByIDTest() {
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

    @Test
    void getProductByIdForModeration_ProductFound() {
        Product product = new Product();
        product.setProductId(1L);
        ProductModerationDTO dto = new ProductModerationDTO();
        dto.setProductId(1L);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productModerationMapper.mapTo(product)).thenReturn(dto);

        ProductModerationDTO result = productService.getProductByIdForModeration(1L);

        assertNotNull(result);
        assertEquals(1L, result.getProductId());
        verify(productRepository, times(1)).findById(1L);
        verify(productModerationMapper, times(1)).mapTo(product);
    }

    @Test
    void getProductByIdForModeration_ProductNotFound() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductByIdForModeration(1L);
        });

        String expectedMessage = "Product not found with ID: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepository, times(1)).findById(1L);
        verify(productModerationMapper, times(0)).mapTo(any(Product.class));
    }



}
