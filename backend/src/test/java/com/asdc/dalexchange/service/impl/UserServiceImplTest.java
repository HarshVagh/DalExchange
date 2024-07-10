package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.model.VerificationCode;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.repository.VerificationCodeRepository;
import com.asdc.dalexchange.service.EmailService;
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

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @Mock
    private EmailService emailService;


    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewCustomers() {
        when(userRepository.countUsersJoinedInLast30Days()).thenReturn(100L);

        long result = userServiceImpl.newCustomers();

        assertEquals(100L, result);
    }

    @Test
    void testCustomersChange() {
        LocalDateTime now = LocalDateTime.now();
        when(userRepository.countUsersJoinedSince(any(LocalDateTime.class))).thenReturn(50L);
        when(userRepository.countUsersJoinedBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(40L);

        double result = userServiceImpl.customersChange();

        assertEquals(25.0, result);

        // Additional checks to ensure line coverage for null values
        when(userRepository.countUsersJoinedSince(any(LocalDateTime.class))).thenReturn(null);
        when(userRepository.countUsersJoinedBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = userServiceImpl.customersChange();
        assertEquals(0.0, result);
    }

    @Test
    void testGetCurrentDateTime() {
        LocalDateTime result = userServiceImpl.getCurrentDateTime();
        assertNotNull(result);
    }

    @Test
    void testViewUserDetails() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userServiceImpl.viewUserDetails(1L);

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

        User result = userServiceImpl.editUserDetails(1L, updatedUser);

        assertEquals("newUsername", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testEditUserDetailsNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.editUserDetails(1L, new User());
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

        User result = userServiceImpl.activateUser(1L);

        assertTrue(result.getActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testActivateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.activateUser(1L);
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

        User result = userServiceImpl.deactivateUser(1L);

        assertFalse(result.getActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeactivateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.deactivateUser(1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userServiceImpl.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testResetPassword() {
        User user = new User();
        user.setUserId(1L);
        user.setPassword("oldPassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userServiceImpl.resetPassword(1L, "newPassword");

        assertEquals("newPassword", result.getPassword());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testResetPasswordNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.resetPassword(1L, "newPassword");
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void registerUserTest() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(user);
        doAnswer(invocation -> {
            VerificationCode code = invocation.getArgument(0);
            code.setCode("123456");
            code.setExpiryDate(LocalDateTime.now().plusHours(1));
            return code;
        }).when(verificationCodeRepository).save(any(VerificationCode.class));

        User registeredUser = userServiceImpl.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(user.getEmail(), registeredUser.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
        verify(verificationCodeRepository, times(1)).save(any(VerificationCode.class));
        verify(emailService, times(1)).sendEmail(eq(user.getEmail()), eq("Verify your email"), anyString());
    }

    @Test
    public void verifyUserTest() {
        String email = "test@dal.ca";
        String code = "123456";

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setCode(code);
        verificationCode.setExpiryDate(LocalDateTime.now().plusHours(1));

        when(verificationCodeRepository.findByEmailAndCode(anyString(), anyString())).thenReturn(Optional.of(verificationCode));

        boolean isVerified = userServiceImpl.verifyUser(email, code);

        assertTrue(isVerified);
        verify(verificationCodeRepository, times(1)).findByEmailAndCode(email, code);
    }

    @Test
    public void verifyUserTest_Failure() {
        String email = "test@dal.ca";
        String code = "123456";

        when(verificationCodeRepository.findByEmailAndCode(anyString(), anyString())).thenReturn(Optional.empty());

        boolean isVerified = userServiceImpl.verifyUser(email, code);

        assertFalse(isVerified);
        verify(verificationCodeRepository, times(1)).findByEmailAndCode(email, code);
    }
}
