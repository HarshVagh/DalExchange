package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.EditProfileDTO;
import com.asdc.dalexchange.mappers.impl.EditProfileMapperImpl;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProfilePageService;
import com.asdc.dalexchange.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfilePageServiceImpl implements ProfilePageService {

    private final UserRepository userRepository;
    private final EditProfileMapperImpl editProfileMapper;
    private final ModelMapper modelMapper;

    @Override
    public EditProfileDTO editUserDetails(EditProfileDTO editProfileDTO) {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        log.info("Editing user details for userId: {}", userId);

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            modelMapper.map(editProfileDTO, user);
            userRepository.save(user);
            log.info("User details updated successfully for userId: {}", userId);
            return editProfileMapper.mapTo(user);
        } else {
            log.error("User not found with id: {}", userId);
            throw new RuntimeException("User not found with id " + userId);
        }
    }

    @Override
    public EditProfileDTO editGetUserDetails() {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        log.info("Fetching user details for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new RuntimeException("User not found");
                });

        log.info("Fetched user details successfully for userId: {}", userId);
        return modelMapper.map(user, EditProfileDTO.class);
    }
}
