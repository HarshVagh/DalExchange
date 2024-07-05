package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.mapper.impl.ProductDetailsMapperImpl;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductDetailsMapperImplTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductDetailsMapperImpl productDetailsMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapToProductDetailsDTO() {
        // Mock Product data
        Long productId = 1L;
        LocalDateTime joiningDate = LocalDateTime.now();
        Product product = createMockProduct(productId, joiningDate);

        // Mock ModelMapper behavior
        ProductDetailsDTO expectedDTO = createExpectedProductDetailsDTO(productId, joiningDate);
        when(modelMapper.map(product, ProductDetailsDTO.class)).thenReturn(expectedDTO);

        // Call the method under test
        ProductDetailsDTO resultDTO = productDetailsMapper.mapTo(product);

        // Assertions
        assertEquals(expectedDTO.getProductId(), resultDTO.getProductId());
        assertEquals(expectedDTO.getSellerJoiningDate(), resultDTO.getSellerJoiningDate());
        assertEquals(expectedDTO.getCategory(), resultDTO.getCategory());
        // Add more assertions as needed
    }

    private Product createMockProduct(Long productId, LocalDateTime joiningDate) {
        Product product = new Product();
        product.setProductId(productId);
        product.setSeller(createMockUser());
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(99.99);
        product.setCategory(createMockProductCategory());
        product.setProductCondition(ProductCondition.NEW);
        product.setUseDuration("1 year");
        product.setShippingType(ShippingType.STANDARD);
        product.setQuantityAvailable(10);
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    private ProductCategory createMockProductCategory() {
        ProductCategory category = new ProductCategory();
        category.setCategoryId(1L);
        category.setName("Test Category");
        // Set other attributes as needed
        return category;
    }

    private User createMockUser() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        // Set other attributes as needed
        return user;
    }

    private ProductDetailsDTO createExpectedProductDetailsDTO(Long productId, LocalDateTime joiningDate) {
        ProductDetailsDTO dto = new ProductDetailsDTO();
        dto.setProductId(productId);
        dto.setSellerJoiningDate(joiningDate);
        dto.setCategory("Test Category");
        // Set other attributes as needed
        return dto;
    }
}
