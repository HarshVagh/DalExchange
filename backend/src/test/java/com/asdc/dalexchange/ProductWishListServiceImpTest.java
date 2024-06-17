package com.asdc.dalexchange;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.imp.ProductWishListServiceImp;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductWishListServiceImpTest {

    @Mock
    private ProductWishlistRepository productWishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductWishListServiceImp productWishListService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
    public void testMarkProductAsFavorite_AddToWishlist() {
        // Mock user and product
        User user = new User();
        Product product = new Product();

        // Mock repository behaviors
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(product));
        when(productWishlistRepository.findByUserIdAndProductId(user, product)).thenReturn(null); // Simulate product not in wishlist

        // Create a new ProductWishlist instance to return when save is called
        ProductWishlist savedWishlistItem = new ProductWishlist();
        savedWishlistItem.setUserId(user);
        savedWishlistItem.setProductId(product);
        when(productWishlistRepository.save(any(ProductWishlist.class))).thenReturn(savedWishlistItem);

        // Test method call
        ProductWishlist result = productWishListService.markProductAsFavorite(1L, 1L);

        // Assertions
        assertNotNull(result);
        assertEquals(user, result.getUserId());
        assertEquals(product, result.getProductId());

        // Verify save method was called once
        verify(productWishlistRepository, times(1)).save(any(ProductWishlist.class));
        verify(productWishlistRepository, times(0)).delete(any(ProductWishlist.class)); // Ensure delete was not called
    }

    @Test
    public void testMarkProductAsFavorite_RemoveFromWishlist() {
        // Mock user and product
        User user = new User();
        Product product = new Product();

        // Mock repository behaviors
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(product));
        when(productWishlistRepository.findByUserIdAndProductId(user, product)).thenReturn(new ProductWishlist()); // Simulate product in wishlist

        // Test method call
        ProductWishlist result = productWishListService.markProductAsFavorite(1L, 1L);

        // Assertions
        assertNull(result); // Expecting null because item was removed

        // Verify delete method was called once
        verify(productWishlistRepository, times(1)).delete(any(ProductWishlist.class));
        verify(productWishlistRepository, times(0)).save(any(ProductWishlist.class)); // Ensure save was not called
    }

    @Test
    public void testMarkProductAsFavorite_UserNotFound() {
        // Mock repository behavior to throw exception
        when(userRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("User not found"));

        // Test and assert exception
        assertThrows(ResourceNotFoundException.class, () -> {
            productWishListService.markProductAsFavorite(1L, 1L);
        });

        // Verify repository method was called once
        verify(productRepository, times(0)).findById(anyLong());
        verify(productWishlistRepository, times(0)).save(any(ProductWishlist.class));
        verify(productWishlistRepository, times(0)).delete(any(ProductWishlist.class));
    }

    @Test
    public void testMarkProductAsFavorite_ProductNotFound() {
        // Mock user and repository behavior
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new User()));
        when(productRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Product not found"));

        // Test and assert exception
        assertThrows(ResourceNotFoundException.class, () -> {
            productWishListService.markProductAsFavorite(1L, 1L);
        });

        // Verify repository methods were called once
        verify(userRepository, times(1)).findById(anyLong());
        verify(productWishlistRepository, times(0)).save(any(ProductWishlist.class));
        verify(productWishlistRepository, times(0)).delete(any(ProductWishlist.class));
    }
}
