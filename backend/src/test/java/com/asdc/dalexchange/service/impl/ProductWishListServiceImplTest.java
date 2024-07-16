package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.PurchaseProductDTO;
import com.asdc.dalexchange.dto.SavedProductDTO;
import com.asdc.dalexchange.mappers.impl.PurchaseProductMapperImpl;
import com.asdc.dalexchange.mappers.impl.SavedProductMapperImpl;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import org.springframework.data.domain.Example;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.specifications.ProductWishlistSpecification;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductWishListServiceImplTest {

    @Mock
    private ProductWishlistRepository productWishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SavedProductMapperImpl savedProductMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PurchaseProductMapperImpl purchaseProductMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductWishListServiceImpl productWishListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testMarkProductAsFavorite_whenUserNotFound() {
        // Arrange
        long userId = 1L;
        long productId = 1L;

        // Mock repository behaviors
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            productWishListService.markProductAsFavorite(userId, productId);
        });
        verify(productRepository, never()).findById(any());
        verify(productWishlistRepository, never()).findAll((Example<ProductWishlist>) any());
        verify(productWishlistRepository, never()).save(any());
    }

    @Test
    void testMarkProductAsFavorite_whenProductNotFound() {
        // Arrange
        long userId = 1L;
        long productId = 1L;

        User user = new User();
        user.setUserId(userId);

        // Mock repository behaviors
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            productWishListService.markProductAsFavorite(userId, productId);
        });
        verify(productWishlistRepository, never()).findAll((Example<ProductWishlist>) any());
        verify(productWishlistRepository, never()).save(any());
    }

    @Test
    @Transactional
    public void testMarkProductAsFavorite_removesProductFromFavorites() {
        long userId = 1L;
        long productId = 1L;

        User user = new User();
        user.setUserId(userId);

        Product product = new Product();
        product.setProductId(productId);

        ProductWishlist existingWishlist = new ProductWishlist();
        existingWishlist.setUserId(user);
        existingWishlist.setProductId(product);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productWishlistRepository.findAll(any(Specification.class))).thenReturn(List.of(existingWishlist));

        boolean result = productWishListService.markProductAsFavorite(userId, productId);

        assertFalse(result);
        verify(productWishlistRepository, times(1)).findAll(any(Specification.class));
        verify(productWishlistRepository, times(1)).deleteAll(any(List.class));
    }

    @Test
    void testGetAllsavedProduct() {
        long userId = 1L;

        ProductWishlist productWishlist1 = new ProductWishlist();
        Product product1 = new Product();
        product1.setProductId(1L);
        productWishlist1.setProductId(product1);

        ProductWishlist productWishlist2 = new ProductWishlist();
        Product product2 = new Product();
        product2.setProductId(2L);
        productWishlist2.setProductId(product2);

        when(productWishlistRepository.findAll(any(Specification.class)))
                .thenReturn(Arrays.asList(productWishlist1, productWishlist2));
        when(productRepository.findByProductIdIn(anyList()))
                .thenReturn(Arrays.asList(product1, product2));

        SavedProductDTO savedProductDTO1 = new SavedProductDTO();
        SavedProductDTO savedProductDTO2 = new SavedProductDTO();

        when(savedProductMapper.mapTo(product1)).thenReturn(savedProductDTO1);
        when(savedProductMapper.mapTo(product2)).thenReturn(savedProductDTO2);

        List<SavedProductDTO> result = productWishListService.GetAllsavedProduct(userId);

        assertEquals(2, result.size());
        verify(productWishlistRepository, times(1)).findAll(any(Specification.class));
        verify(productRepository, times(1)).findByProductIdIn(anyList());
        verify(savedProductMapper, times(1)).mapTo(product1);
        verify(savedProductMapper, times(1)).mapTo(product2);
    }

        @Test
        void testGetallPurchasedProduct_whenOrdersExist() {
            // Arrange
            long userId = 1L;
            OrderDetails orderDetails1 = new OrderDetails();
            orderDetails1.setOrderId(1L); // Set appropriate data for testing
            OrderDetails orderDetails2 = new OrderDetails();
            orderDetails2.setOrderId(2L); // Set appropriate data for testing

            when(orderRepository.findByBuyerUserId(userId))
                    .thenReturn(Arrays.asList(orderDetails1, orderDetails2));

            PurchaseProductDTO purchaseProductDTO1 = new PurchaseProductDTO();
            PurchaseProductDTO purchaseProductDTO2 = new PurchaseProductDTO();

            when(purchaseProductMapper.mapTo(eq(orderDetails1))).thenReturn(purchaseProductDTO1);
            when(purchaseProductMapper.mapTo(eq(orderDetails2))).thenReturn(purchaseProductDTO2);

            // Act
            List<PurchaseProductDTO> result = productWishListService.GetallPurchasedProduct(userId);

            // Assert
            assertEquals(2, result.size());
            verify(purchaseProductMapper, times(1)).mapTo(eq(orderDetails1));
            verify(purchaseProductMapper, times(1)).mapTo(eq(orderDetails2));
        }

        @Test
        void testGetallPurchasedProduct_whenNoOrders() {
            // Arrange
            long userId = 1L;
            when(orderRepository.findByBuyerUserId(userId))
                    .thenReturn(Collections.emptyList());

            // Act
            List<PurchaseProductDTO> result = productWishListService.GetallPurchasedProduct(userId);

            // Assert
            assertEquals(0, result.size());
            verify(purchaseProductMapper, never()).mapTo(any()); // Ensure mapTo is never called when there are no orders
        }
    @Test
    public void testCheckProductIsFavoriteByGivenUser_whenFavorite() {
        long userId = 1L;
        long productId = 1L;

        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserIdAndProductId(userId, productId);
        when(productWishlistRepository.count(any(Specification.class))).thenReturn(1L);

        boolean result = productWishListService.checkProductIsFavoriteByGivenUser(userId, productId);

        assertTrue(result);
        verify(productWishlistRepository, times(1)).count(any(Specification.class));
    }

    @Test
    public void testCheckProductIsFavoriteByGivenUser_whenNotFavorite() {
        long userId = 1L;
        long productId = 1L;

        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserIdAndProductId(userId, productId);
        when(productWishlistRepository.count(any(Specification.class))).thenReturn(0L);

        boolean result = productWishListService.checkProductIsFavoriteByGivenUser(userId, productId);

        assertFalse(result);
        verify(productWishlistRepository, times(1)).count(any(Specification.class));
    }
}

