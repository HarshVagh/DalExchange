package com.asdc.dalexchange;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductWishListServiceImpTest {

    @Mock
    private ProductWishlistRepository productWishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductWishListServiceImp productWishListService;

    private Long userId;
    private Long productId;
    private User user;
    private Product product;
    private ProductWishlist productWishlist;

    @BeforeEach
    public void setUp() {
        userId = 1L;
        productId = 1L;

        user = new User();
        user.setUserId(userId);

        product = new Product();
        product.setProductId(productId);

        productWishlist = new ProductWishlist();
        productWishlist.setUserId(user);
        productWishlist.setProductId(product);
    }

    @Test
    public void testMarkProductAsFavoriteAddToWishlist() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productWishlistRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());
        when(productWishlistRepository.save(any(ProductWishlist.class))).thenReturn(productWishlist);

        ProductWishlist result = productWishListService.markProductAsFavorite(userId, productId);

        assertNotNull(result);
        assertEquals(productWishlist, result);
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(productWishlistRepository, times(1)).findOne(any(Specification.class));
        verify(productWishlistRepository, times(1)).save(any(ProductWishlist.class));
    }

    @Test
    public void testMarkProductAsFavoriteRemoveFromWishlist() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productWishlistRepository.findOne(any(Specification.class))).thenReturn(Optional.of(productWishlist));

        ProductWishlist result = productWishListService.markProductAsFavorite(userId, productId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(productWishlistRepository, times(1)).findOne(any(Specification.class));
        verify(productWishlistRepository, times(1)).delete(productWishlist);
    }

    @Test
    public void testCheckProductIsFavoriteByGivenUser() {
        when(productWishlistRepository.count(any(Specification.class))).thenReturn(1L);

        boolean result = productWishListService.checkProductIsFavoriteByGivenUser(userId, productId);

        assertTrue(result);
        verify(productWishlistRepository, times(1)).count(any(Specification.class));
    }

    @Test
    public void testCheckProductIsNotFavoriteByGivenUser() {
        when(productWishlistRepository.count(any(Specification.class))).thenReturn(0L);

        boolean result = productWishListService.checkProductIsFavoriteByGivenUser(userId, productId);

        assertFalse(result);
        verify(productWishlistRepository, times(1)).count(any(Specification.class));
    }

    @Test
    public void testGetProductIdsByUserId() {
        List<ProductWishlist> wishlists = Arrays.asList(productWishlist);
        List<Long> productIds = Arrays.asList(productId);
        when(productWishlistRepository.findAll(any(Specification.class))).thenReturn(wishlists);
        when(productRepository.findByProductIdIn(productIds)).thenReturn(Arrays.asList(product));

        List<Product> result = productWishListService.getProductIdsByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
        verify(productWishlistRepository, times(1)).findAll(any(Specification.class));
        verify(productRepository, times(1)).findByProductIdIn(productIds);
    }

    @Test
    public void testGetProductIdsByUserIdNoWishlistItems() {
        when(productWishlistRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        List<Product> result = productWishListService.getProductIdsByUserId(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productWishlistRepository, times(1)).findAll(any(Specification.class));
        verify(productRepository, times(0)).findByProductIdIn(anyList());
    }

    @Test
    public void testMarkProductAsFavoriteUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productWishListService.markProductAsFavorite(userId, productId);
        });

        String expectedMessage = "User not found with id " + userId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(0)).findById(productId);
        verify(productWishlistRepository, times(0)).findOne(any(Specification.class));
        verify(productWishlistRepository, times(0)).save(any(ProductWishlist.class));
        verify(productWishlistRepository, times(0)).delete(any(ProductWishlist.class));
    }

    @Test
    public void testMarkProductAsFavoriteProductNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productWishListService.markProductAsFavorite(userId, productId);
        });

        String expectedMessage = "Product not found with id " + productId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(productWishlistRepository, times(0)).findOne(any(Specification.class));
        verify(productWishlistRepository, times(0)).save(any(ProductWishlist.class));
        verify(productWishlistRepository, times(0)).delete(any(ProductWishlist.class));
    }
}
