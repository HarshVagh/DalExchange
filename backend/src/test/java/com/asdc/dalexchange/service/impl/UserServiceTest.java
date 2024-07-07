package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;

import com.asdc.dalexchange.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @Spy
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewCustomers() {
        when(userRepository.countUsersJoinedInLast30Days()).thenReturn(10L);
        long newCustomers = userService.newCustomers();
        assertEquals(10L, newCustomers);
    }

    @Test
    void testCustomersChange() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 7, 1, 0, 0);
        doReturn(fixedDateTime).when(userService).getCurrentDateTime();

        LocalDateTime startOfCurrentPeriod = fixedDateTime.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        when(userRepository.countUsersJoinedSince(startOfCurrentPeriod)).thenReturn(20L);
        when(userRepository.countUsersJoinedBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(10L);

        double percentageChange = userService.customersChange();
        assertEquals(100.0, percentageChange, 0.01);
    }

    @Test
    void testViewUserDetails() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.viewUserDetails(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getUserId());
    }

    @Test
    void testEditUserDetails() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.editUserDetails(user);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void testActivateUser() {
        User user = new User();
        user.setUserId(1L);
        user.setActive(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.activateUser(1L);
        assertTrue(result.getActive());
    }

    @Test
    void testDeactivateUser() {
        User user = new User();
        user.setUserId(1L);
        user.setActive(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.deactivateUser(1L);
        assertFalse(result.getActive());
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
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.resetPassword(1L, "newPassword");
        assertEquals("newPassword", result.getPassword());
    }
}
