package com.asdc.dalexchange.service.impl;
import com.asdc.dalexchange.dto.EditProfileDTO;
import com.asdc.dalexchange.mappers.impl.EditProfileMapperImpl;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfilePageServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EditProfileMapperImpl editProfileMapper;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProfilePageServiceImpl profilePageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testEditUserDetails_Success() {
        // Mock data
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        EditProfileDTO editProfileDTO = createEditProfileDTO();
        User user = createUser();

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock mapper behavior
        when(modelMapper.map(editProfileDTO, User.class)).thenReturn(user);

        // Mock repository save behavior
        when(userRepository.save(user)).thenReturn(user);

        // Mock mapper for returning DTO
        when(editProfileMapper.mapTo(user)).thenReturn(editProfileDTO);

        // Call the service method
        EditProfileDTO updatedProfile = profilePageService.editUserDetails(editProfileDTO);

        // Assertions
        assertNotNull(updatedProfile);
        assertEquals(editProfileDTO.getUsername(), updatedProfile.getUsername());
        assertEquals(editProfileDTO.getEmail(), updatedProfile.getEmail());
        // Add more assertions based on your DTO and expected behavior
    }

    @Test
    void testEditUserDetails_UserNotFound() {
        // Mock data
        Long userId = 1L;
        EditProfileDTO editProfileDTO = createEditProfileDTO();

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the service method and expect RuntimeException
        assertThrows(RuntimeException.class, () -> {
            profilePageService.editUserDetails(editProfileDTO);
        });
    }

    @Test
    void testGetUserDetails_Success() {
        // Mock data
        Long userId = 1L;
        User user = createUser();

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock mapper behavior
        when(modelMapper.map(user, EditProfileDTO.class)).thenReturn(createEditProfileDTO());

        // Call the service method
        EditProfileDTO userDetails = profilePageService.editGetUserDetails();

        // Assertions
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getEmail(), userDetails.getEmail());
        // Add more assertions based on your DTO and expected behavior
    }

    // Helper methods for creating mock data
    private EditProfileDTO createEditProfileDTO() {
        EditProfileDTO dto = new EditProfileDTO();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");
        // Set other fields as needed for tests
        return dto;
    }

    private User createUser() {
        User user = new User();
        user.getUserId();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        // Set other fields as needed for tests
        return user;
    }

    // Add more test cases to cover edge cases and other methods as necessary

}





