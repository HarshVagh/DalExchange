package com.asdc.dalexchange.service.impl.profilepage;

import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.service.imp.ProfilePageServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfilePageServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductWishlistService productWishlistService;

    @Mock
    private ProductRatingService productRatingService;

    @Mock
    private OrderService orderService;


    @InjectMocks
    private ProfilePageServiceImp profilePageServiceImp;

    private User user;
    private UserDTO userDTO;

    @Mock
    private SoldItemService soldItemService;

    @Mock
    private ModelMapper modelMapper;


    @BeforeEach
    void setUp() {
        profilePageServiceImp = new ProfilePageServiceImp();
        profilePageServiceImp.userRepository = userRepository;
        profilePageServiceImp.productWishlistService = productWishlistService;
        profilePageServiceImp.productRatingService = productRatingService;
        profilePageServiceImp.orderService = orderService;
        profilePageServiceImp.soldItemService = soldItemService;
        profilePageServiceImp.modelMapper = modelMapper;
        user = new User();
        userDTO = new UserDTO();
    }

    @Test
    void testEditUserDetails() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("newUsername");

        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setUsername("oldUsername");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(new UserDTO());

        UserDTO result = profilePageServiceImp.editUserDetails(user);

        assertEquals("newUsername", existingUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
        verify(modelMapper, times(1)).map(any(User.class), eq(UserDTO.class));
    }

    @Test
    void testGetAllSavedProduct() {
        Long userId = 1L;
        Product product = new Product();
        product.setTitle("Product1");

        when(productWishlistService.getProductIdsByUserId(userId)).thenReturn(Collections.singletonList(product));

        List<SavedProductDTO> result = profilePageServiceImp.GetAllsavedProduct(userId);

        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getTitle());
        verify(productWishlistService, times(1)).getProductIdsByUserId(userId);
    }

    @Test
    void testGetAllPurchasedProduct() {
        Long userId = 1L;
        OrderDetails orderDetails = new OrderDetails();
        Product product = new Product();
        product.setTitle("Product1");
        orderDetails.setProductId(product);

        when(orderService.getOrdersByUserId(userId)).thenReturn(Collections.singletonList(orderDetails));

        List<PurchaseProductDTO> result = profilePageServiceImp.GetallPurchasedProduct(userId);

        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getTitle());
        verify(orderService, times(1)).getOrdersByUserId(userId);
    }

    @Test
    void testGetAllSoldProduct() {
        Long userId = 1L;
        SoldItem soldItem = new SoldItem();
        Product product = new Product();
        product.setTitle("Product1");
        soldItem.setProduct(product);

        when(soldItemService.getSoldItemsBySellerId(userId)).thenReturn(Collections.singletonList(soldItem));

        List<SoldItemDTO> result = profilePageServiceImp.GetallSoldProduct(userId);

        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getTitle());
        verify(soldItemService, times(1)).getSoldItemsBySellerId(userId);
    }

    @Test
    void testGetAllProductRating() {
        Long userId = 1L;
        ProductRating productRating = new ProductRating();
        Product product = new Product();
        product.setTitle("Product1");
        productRating.setProduct(product);

        when(productRatingService.getProductRatingsByUserId(userId)).thenReturn(Collections.singletonList(productRating));

        List<ProductRatingDTO> result = profilePageServiceImp.GetAllProductRating(userId);

        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getTitle());
        verify(productRatingService, times(1)).getProductRatingsByUserId(userId);
    }



 @Test
    void testUpdateUserUsername() {
        userDTO.setUsername("newUsername");

        profilePageServiceImp.editUserDetails(user);

        assertEquals("newUsername", user.getUsername());
    }

    @Test
    void testUpdateUserEmail() {
        userDTO.setEmail("newEmail@example.com");

        profilePageServiceImp.editUserDetails(user);

        assertEquals("newEmail@example.com", user.getEmail());
    }

    @Test
    void testUpdateUserFullName() {
        userDTO.setFullName("New Full Name");

        profilePageServiceImp.editUserDetails(user);

        assertEquals("New Full Name", user.getFullName());
    }

    @Test
    void testUpdateUserPassword() {
        userDTO.setPassword("newPassword");

        profilePageServiceImp.editUserDetails(user);

        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void testUpdateUserPhoneNo() {
        userDTO.setPhoneNo("1234567890");

        profilePageServiceImp.editUserDetails(user);

        assertEquals("1234567890", user.getPhoneNo());
    }

    @Test
    void testUpdateMultipleFields() {
        userDTO.setUsername("newUsername");
        userDTO.setEmail("newEmail@example.com");
        userDTO.setFullName("New Full Name");
        userDTO.setPassword("newPassword");
        userDTO.setPhoneNo("1234567890");

        profilePageServiceImp.editUserDetails(user);

        assertEquals("newUsername", user.getUsername());
        assertEquals("newEmail@example.com", user.getEmail());
        assertEquals("New Full Name", user.getFullName());
        assertEquals("newPassword", user.getPassword());
        assertEquals("1234567890", user.getPhoneNo());
    }

    @Test
    void testNoUpdateWhenAllFieldsNull() {
        profilePageServiceImp.editUserDetails(user);

        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getFullName());
        assertNull(user.getPassword());
        assertNull(user.getPhoneNo());
    }

}

