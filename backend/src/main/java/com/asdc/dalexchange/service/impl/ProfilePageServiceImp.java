package com.asdc.dalexchange.service.impl;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.mappers.impl.*;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.*;
import com.asdc.dalexchange.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProfilePageServiceImp implements ProfilePageService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EditProfileMapperImpl editProfileMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EditProfileDTO editUserDetails(Long userId, EditProfileDTO editProfileDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            modelMapper.map(editProfileDTO, user);
            userRepository.save(user);
            return editProfileMapper.mapTo(user);
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
    }

        @Override
    public EditProfileDTO editGetUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, EditProfileDTO.class);
    }
}
