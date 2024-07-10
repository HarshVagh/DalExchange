package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewCustomers() {
        when(userRepository.countUsersJoinedInLast30Days()).thenReturn(100L);

        long result = userService.newCustomers();

        assertEquals(100L, result);
    }

    @Test
    void testCustomersChange() {
        LocalDateTime now = LocalDateTime.now();
        when(userRepository.countUsersJoinedSince(any(LocalDateTime.class))).thenReturn(50L);
        when(userRepository.countUsersJoinedBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(40L);

        double result = userService.customersChange();

        assertEquals(25.0, result);

        // Additional checks to ensure line coverage for null values
        when(userRepository.countUsersJoinedSince(any(LocalDateTime.class))).thenReturn(null);
        when(userRepository.countUsersJoinedBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = userService.customersChange();
        assertEquals(0.0, result);
    }

    @Test
    void testGetCurrentDateTime() {
        LocalDateTime result = userService.getCurrentDateTime();
        assertNotNull(result);
    }

    @Test
    void testViewUserDetails() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.viewUserDetails(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testEditUserDetails() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("oldUsername");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setUsername("newUsername");

        User result = userService.editUserDetails(1L, updatedUser);

        assertEquals("newUsername", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testEditUserDetailsNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.editUserDetails(1L, new User());
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testActivateUser() {
        User user = new User();
        user.setUserId(1L);
        user.setActive(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.activateUser(1L);

        assertTrue(result.getActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testActivateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.activateUser(1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeactivateUser() {
        User user = new User();
        user.setUserId(1L);
        user.setActive(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.deactivateUser(1L);

        assertFalse(result.getActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeactivateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deactivateUser(1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testResetPassword() {
        User user = new User();
        user.setUserId(1L);
        user.setPassword("oldPassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.resetPassword(1L, "newPassword");

        assertEquals("newPassword", result.getPassword());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testResetPasswordNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.resetPassword(1L, "newPassword");
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }
}
