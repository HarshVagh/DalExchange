/*
package com.asdc.dalexchange;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.mapper.impl.ProductDetailsMapperImpl;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.imp.ProductDetailsServiceImp;
import com.asdc.dalexchange.service.imp.SellerServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductDetailsServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerServiceImp sellerService;

    @InjectMocks
    private ProductDetailsServiceImp productDetailsService;

   // private ProductDetailsMapperImpl productDetailsMapper = new ProductDetailsMapperImpl(ModelMapper productRepository);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDetailsOfProduct_SellerNotAvailable() {
        // Arrange
        Long productId = 1L;
        Long userId = 1L;
        Product product = new Product();
        product.setProductId(productId);
        product.setTitle("Test Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(sellerService.getSellerById(anyLong())).thenReturn(Optional.empty()); // Mock seller not available

        // Act
        ProductDetailsDTO productDTO = productDetailsService.DetailsOfProduct(userId,productId);

        // Assert
        assertEquals(productId, productDTO.getProductId());
        assertEquals("Test Product", productDTO.getTitle());
        assertEquals(null, productDTO.getSellerName()); // Assert seller is null
    }

    @Test
    public void testDetailsOfProduct_ValidProductAndSeller() {
        // Arrange
        Long productId = 2L;
        Long userId = 2L;
        Product product = new Product();
        product.setProductId(productId);
        product.setTitle("Test Product");

        User seller = new User();
        seller.setUserId(101L);
        seller.setUsername("seller101");

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setSellerId(seller.getUserId());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(sellerService.getSellerById(anyLong())).thenReturn(Optional.of(sellerDTO)); // Mock seller available

        // Act
        ProductDetailsDTO productDTO = productDetailsService.DetailsOfProduct(userId,productId);

        // Assert
        assertEquals(productId, productDTO.getProductId());
        assertEquals("Test Product", productDTO.getTitle());
        assertEquals(sellerDTO, productDTO.getSellerName()); // Assert seller is correctly mapped
    }
}
*/
