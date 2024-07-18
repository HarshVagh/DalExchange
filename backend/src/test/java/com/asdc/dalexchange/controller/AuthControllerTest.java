package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.model.VerificationRequest;
import com.asdc.dalexchange.service.impl.UserServiceImpl;
import com.asdc.dalexchange.util.CurrentUserUtil;
import com.asdc.dalexchange.util.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CurrentUserUtil currentUserUtil;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @Mock
    private MultipartFile profilePicture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void signupTest() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doAnswer(invocation -> null).when(userService).registerUser(any(User.class));

        ResponseEntity<?> response = authController.signup(
                "testuser",
                "password",
                "test@dal.ca",
                "Test User",
                "1234567890",
                profilePicture,
                "student",
                "This is a bio"
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully. Please check your email for verification code.", response.getBody());
    }

    @Test
    public void signupTest_InvalidEmail() {
        ResponseEntity<?> response = authController.signup(
                "testuser",
                "password",
                "test@gmail.com",
                "Test User",
                "1234567890",
                profilePicture,
                "student",
                "This is a bio"
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email must be a @dal.ca address", response.getBody());
    }

    @Test
    public void signupTest_Exception() {
        when(passwordEncoder.encode(anyString())).thenThrow(new RuntimeException());

        ResponseEntity<?> response = authController.signup(
                "testuser",
                "password",
                "test@dal.ca",
                "Test User",
                "1234567890",
                profilePicture,
                "student",
                "This is a bio"
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error registering user.", response.getBody());
    }

    @Test
    public void loginTest() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@dal.ca");
        when(jwtUtils.generateToken(anyString())).thenReturn("jwtToken");

        ResponseEntity<?> response = authController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Map.of("token", "jwtToken"), response.getBody());
    }

    @Test
    public void loginTest_InvalidCredentials() {
        User user = new User();
        user.setEmail("test@dal.ca");
        user.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException());

        ResponseEntity<?> response = authController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    public void verifyEmailTest() {
        VerificationRequest request = new VerificationRequest();
        request.setEmail("test@dal.ca");
        request.setCode("123456");

        when(userService.verifyUser(anyString(), anyString())).thenReturn(true);

        ResponseEntity<?> response = authController.verifyEmail(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User verified successfully", response.getBody());
    }

    @Test
    public void verifyEmailTest_InvalidCode() {
        VerificationRequest request = new VerificationRequest();
        request.setEmail("test@dal.ca");
        request.setCode("123456");

        when(userService.verifyUser(anyString(), anyString())).thenReturn(false);

        ResponseEntity<?> response = authController.verifyEmail(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid verification code or code has expired", response.getBody());
    }

    @Test
    public void verifyEmailTest_Exception() {
        VerificationRequest request = new VerificationRequest();
        request.setEmail("test@dal.ca");
        request.setCode("123456");

        when(userService.verifyUser(anyString(), anyString())).thenThrow(new RuntimeException());

        ResponseEntity<?> response = authController.verifyEmail(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error verifying user.", response.getBody());
    }

    @Test
    public void getCurrentUser_ShouldReturnUser_WhenUserIsLoggedIn() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setFullName("John Doe");
        mockUser.setEmail("john.doe@example.com");

        when(currentUserUtil.getCurrentUser()).thenReturn(mockUser);

        mockMvc.perform(get("/auth/current-user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(currentUserUtil, times(1)).getCurrentUser();
    }

    @Test
    public void getCurrentUser_ShouldReturnUnauthorized_WhenUserIsNotLoggedIn() throws Exception {
        when(currentUserUtil.getCurrentUser()).thenReturn(null);

        mockMvc.perform(get("/auth/current-user"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("No user is currently logged in"));

        verify(currentUserUtil, times(1)).getCurrentUser();
    }
}
