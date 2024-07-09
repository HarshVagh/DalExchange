package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductWishListServiceImpTest {

    @Mock
    private ProductWishlistRepository productWishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductWishListServiceImp productWishlistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMarkProductAsFavorite_AddToFavorites() {
        // Mock data
        long userId = 1L;
        long productId = 100L;
        User user = new User();
        user.setUserId(userId);
        Product product = new Product();
        product.setProductId(productId);
        ProductWishlist productWishlist = new ProductWishlist();
        productWishlist.setUserId(user);
        productWishlist.setProductId(product);

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productWishlistRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());
        when(productWishlistRepository.save(any(ProductWishlist.class))).thenReturn(productWishlist);

        // Call method under test
        ProductWishlist result = productWishlistService.markProductAsFavorite(userId, productId);

        // Assertions
        assertNotNull(result);
        assertEquals(userId, result.getUserId().getUserId());
        assertEquals(productId, result.getProductId().getProductId());
    }

    @Test
    public void testMarkProductAsFavorite_RemoveFromFavorites() {
        // Mock data
        long userId = 1L;
        long productId = 100L;
        User user = new User();
        user.setUserId(userId);
        Product product = new Product();
        product.setProductId(productId);
        ProductWishlist productWishlist = new ProductWishlist();
        productWishlist.setUserId(user);
        productWishlist.setProductId(product);
        List<ProductWishlist> existingWishlistItems = new ArrayList<>();
        existingWishlistItems.add(productWishlist);

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productWishlistRepository.findAll(any(Specification.class))).thenReturn(existingWishlistItems);

        // Call method under test
        ProductWishlist result = productWishlistService.markProductAsFavorite(userId, productId);

        // Assertions
        assertNull(result);
        verify(productWishlistRepository, times(1)).deleteAll(existingWishlistItems);
    }

    @Test
    public void testGetProductIdsByUserId() {
        // Mock data
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        Product product1 = new Product();
        product1.setProductId(100L);
        Product product2 = new Product();
        product2.setProductId(101L);

        ProductWishlist productWishlist1 = new ProductWishlist();
        productWishlist1.setUserId(user);
        productWishlist1.setProductId(product1);

        ProductWishlist productWishlist2 = new ProductWishlist();
        productWishlist2.setUserId(user);
        productWishlist2.setProductId(product2);

        List<ProductWishlist> allWishlistedProducts = List.of(productWishlist1, productWishlist2);
        List<Long> productIds = List.of(100L, 101L);
        List<Product> expectedProducts = List.of(product1, product2);

        // Mock repository behavior
        when(productWishlistRepository.findAll(any(Specification.class))).thenReturn(allWishlistedProducts);
        when(productRepository.findByProductIdIn(productIds)).thenReturn(expectedProducts);

        // Call method under test
        List<Product> result = productWishlistService.getProductIdsByUserId(userId);

        // Assertions
        assertEquals(expectedProducts.size(), result.size());
        assertEquals(productIds, result.stream().map(Product::getProductId).toList());
    }

//    @Test
//    public void testCheckProductIsFavoriteByGivenUser_FavoriteExists() {
//        // Mock data
//        long userId = 1L;
//        long productId = 100L;
//        List<ProductWishlist> existingWishlistItems = List.of(new ProductWishlist());
//
//        // Mock repository behavior
//        when(productWishlistRepository.count(any(Specification.class))).thenReturn(1L);
//
//        // Call method under test
//        boolean result = productWishlistService.checkProductIsFavoriteByGivenUser(userId, productId);
//
//        // Assertions
//        assertTrue(result);
//    }

//    @Test
//    public void testCheckProductIsFavoriteByGivenUser_FavoriteDoesNotExist() {
//        // Mock data
//        long userId = 1L;
//        long productId = 100L;
//
//        // Mock repository behavior
//        when(productWishlistRepository.count(any(Specification.class))).thenReturn(0L);
//
//        // Call method under test
//        boolean result = productWishlistService.checkProductIsFavoriteByGivenUser(userId, productId);
//
//        // Assertions
//        assertFalse(result);
//    }
}
