package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.UserDTO;
import com.asdc.dalexchange.enums.Role;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.model.VerificationCode;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.repository.VerificationCodeRepository;
import com.asdc.dalexchange.service.EmailService;
import com.asdc.dalexchange.util.AuthUtil;
import com.asdc.dalexchange.util.CloudinaryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Mock
    private Mapper<User, UserDTO> userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private MultipartFile productImage;

    @Mock
    private CloudinaryUtil cloudinaryUtil;

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
    public void testGetAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setUserId(1L);
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setEmail("user2@example.com");

        List<User> users = Arrays.asList(user1, user2);
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setUserId(user.getUserId());
            dto.setEmail(user.getEmail());
            return dto;
        }).collect(Collectors.toList());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.mapTo(user1)).thenReturn(userDTOs.get(0));
        when(userMapper.mapTo(user2)).thenReturn(userDTOs.get(1));

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("user1@example.com", result.get(0).getEmail());
        assertEquals("user2@example.com", result.get(1).getEmail());
    }

    @Test
    public void testViewUserDetails() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setEmail("user@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setEmail("user@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.mapTo(user)).thenReturn(userDTO);

        // Act
        Optional<UserDTO> result = userService.viewUserDetails(userId);

        // Assert
        assertEquals(userDTO, result.get());
    }

    @Test
    public void testEditUserDetails() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setEmail("old@example.com");

        UserDTO updatedUserDetails = new UserDTO();
        updatedUserDetails.setEmail("new@example.com");
        updatedUserDetails.setPhoneNo("123456789");
        updatedUserDetails.setFullName("New Name");
        updatedUserDetails.setRole(Role.admin);
        updatedUserDetails.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapTo(user)).thenReturn(updatedUserDetails);

        // Act
        UserDTO result = userService.editUserDetails(userId, updatedUserDetails);

        // Assert
        assertEquals("new@example.com", user.getEmail());
        assertEquals("123456789", user.getPhoneNo());
        assertEquals("New Name", user.getFullName());
        assertEquals(Role.admin, user.getRole());
        assertEquals(true, user.getActive());
        assertEquals(updatedUserDetails, result);
    }

    @Test
    public void testActivateUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setActive(false);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapTo(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.activateUser(userId);

        // Assert
        assertEquals(true, user.getActive());
        assertEquals(userDTO, result);
    }

    @Test
    public void testActivateUser_NotFound() {
        // Arrange
        long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.activateUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testDeactivateUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setActive(true);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setActive(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapTo(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.deactivateUser(userId);

        // Assert
        assertEquals(false, user.getActive());
        assertEquals(userDTO, result);
    }

    @Test
    public void testDeactivateUser_NotFound() {
        // Arrange
        long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deactivateUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
    }



    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
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

        User registeredUser = userService.registerUser(user, productImage);

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

        boolean isVerified = userService.verifyUser(email, code);

        assertTrue(isVerified);
        verify(verificationCodeRepository, times(1)).findByEmailAndCode(email, code);
    }

    @Test
    public void verifyUserTest_Failure() {
        String email = "test@dal.ca";
        String code = "123456";

        when(verificationCodeRepository.findByEmailAndCode(anyString(), anyString())).thenReturn(Optional.empty());

        boolean isVerified = userService.verifyUser(email, code);

        assertFalse(isVerified);
        verify(verificationCodeRepository, times(1)).findByEmailAndCode(email, code);
    }

    @Test
    void testGetCurrentUser() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setUserId(1L);
        expectedUser.setEmail("test@dal.ca");

        try (MockedStatic<AuthUtil> mockedAuthUtil = Mockito.mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(() -> AuthUtil.getCurrentUser(userRepository)).thenReturn(expectedUser);

            // Act
            User actualUser = userService.getCurrentUser();

            // Assert
            assertEquals(expectedUser, actualUser);
            mockedAuthUtil.verify(() -> AuthUtil.getCurrentUser(userRepository), times(1));
        }
    }

    @Test
    void testGetCurrentUser_NoUserFound() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = Mockito.mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(() -> AuthUtil.getCurrentUser(userRepository)).thenReturn(null);

            // Act
            User actualUser = userService.getCurrentUser();

            // Assert
            assertEquals(null, actualUser);
            mockedAuthUtil.verify(() -> AuthUtil.getCurrentUser(userRepository), times(1));
        }
    }
}
