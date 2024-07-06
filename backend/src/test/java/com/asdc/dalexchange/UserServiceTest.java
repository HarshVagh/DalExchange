package com.asdc.dalexchange;

import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.UserService;
import com.asdc.dalexchange.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNewCustomers() {
        // Given
        long expectedNewCustomers = 10L;

        // When
        when(userRepository.countUsersJoinedInLast30Days()).thenReturn(expectedNewCustomers);
        long actualNewCustomers = userService.newCustomers();

        // Then
        assertEquals(expectedNewCustomers, actualNewCustomers);
    }

    @Test
    public void testViewUserDetails_existingUser() {
        // Mocking behavior
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setUsername("testuser");
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        // Calling the method under test
        Optional<User> result = userService.viewUserDetails(1);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        assertEquals(1, result.get().getUserId());
    }

    @Test
    public void testViewUserDetails_userNotFound() {
        // Mocking behavior
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Calling the method under test
        Optional<User> result = userService.viewUserDetails(1);

        // Assertions
        assertFalse(result.isPresent());
    }

    @Test
    public void testEditUserDetails() {
        // Mocking behavior
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setUsername("oldusername");

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Calling the method under test
        User editedUser = userService.editUserDetails(mockUser);

        // Assertions
        assertEquals("oldusername", editedUser.getUsername());
    }

    @Test
    public void testActivateUser_existingUser() {
        // Mocking behavior
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setActive(false);

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Calling the method under test
        User activatedUser = userService.activateUser(1);

        // Assertions
        assertTrue(activatedUser.getActive());
    }

    @Test
    public void testActivateUser_userNotFound() {
        // Mocking behavior
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Calling the method under test
        assertThrows(RuntimeException.class, () -> {
            userService.activateUser(1);
        });
    }

    @Test
    public void testDeactivateUser_existingUser() {
        // Mocking behavior
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setActive(true);

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Calling the method under test
        User deactivatedUser = userService.deactivateUser(1);

        // Assertions
        assertFalse(deactivatedUser.getActive());
    }

    @Test
    public void testDeactivateUser_userNotFound() {
        // Mocking behavior
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Calling the method under test
        assertThrows(RuntimeException.class, () -> {
            userService.deactivateUser(1);
        });
    }

    @Test
    public void testDeleteUser() {
        // Calling the method under test
        userService.deleteUser(1);

        // Verify repository method called with correct argument
        verify(userRepository).deleteById(1);
    }

    @Test
    public void testResetPassword_existingUser() {
        // Mocking behavior
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setPassword("oldpassword");

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Calling the method under test
        User userWithResetPassword = userService.resetPassword(1, "newpassword");

        // Assertions
        assertEquals("newpassword", userWithResetPassword.getPassword());
    }

    @Test
    public void testResetPassword_userNotFound() {
        // Mocking behavior
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Calling the method under test
        assertThrows(RuntimeException.class, () -> {
            userService.resetPassword(1, "newpassword");
        });
    }

    @Test
    public void testCustomersChange_decrease() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalCustomersLast30Days = 80L;
        Long totalCustomersPrevious30Days = 100L;

        // Mocking behavior
        when(userRepository.countUsersJoinedSince(startOfCurrentPeriod)).thenReturn(totalCustomersLast30Days);
        when(userRepository.countUsersJoinedBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(totalCustomersPrevious30Days);

        // When
        double percentageChange = userService.customersChange();

        // Then
        assertEquals(-20.0, percentageChange);
    }

    @Test
    public void testCustomersChange_noChange() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalCustomersLast30Days = 100L;
        Long totalCustomersPrevious30Days = 100L;

        // Mocking behavior
        when(userRepository.countUsersJoinedSince(startOfCurrentPeriod)).thenReturn(totalCustomersLast30Days);
        when(userRepository.countUsersJoinedBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(totalCustomersPrevious30Days);

        // When
        double percentageChange = userService.customersChange();

        // Then
        assertEquals(0.0, percentageChange);
    }

    @Test
    public void testCustomersChange_noPreviousCustomers() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalCustomersLast30Days = 100L;
        Long totalCustomersPrevious30Days = 0L;

        // Mocking behavior
        when(userRepository.countUsersJoinedSince(startOfCurrentPeriod)).thenReturn(totalCustomersLast30Days);
        when(userRepository.countUsersJoinedBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(totalCustomersPrevious30Days);

        // When
        double percentageChange = userService.customersChange();

        // Then
        assertEquals(100.0, percentageChange);
    }

    @Test
    public void testCustomersChange_noCurrentCustomers() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalCustomersLast30Days = 0L;
        Long totalCustomersPrevious30Days = 100L;

        // Mocking behavior
        when(userRepository.countUsersJoinedSince(startOfCurrentPeriod)).thenReturn(totalCustomersLast30Days);
        when(userRepository.countUsersJoinedBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(totalCustomersPrevious30Days);

        // When
        double percentageChange = userService.customersChange();

        // Then
        assertEquals(-100.0, percentageChange);
    }

    @Test
    public void testCustomersChange_noCustomers() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalCustomersLast30Days = 0L;
        Long totalCustomersPrevious30Days = 0L;

        // Mocking behavior
        when(userRepository.countUsersJoinedSince(startOfCurrentPeriod)).thenReturn(totalCustomersLast30Days);
        when(userRepository.countUsersJoinedBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(totalCustomersPrevious30Days);

        // When
        double percentageChange = userService.customersChange();

        // Then
        assertEquals(0.0, percentageChange);
    }

}
