package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.model.User;

import java.util.List;

public interface ProfilePageService {

    // edit the profile of user details
    EditProfileDTO editUserDetails(Long userId , EditProfileDTO editProfileDTO);

    EditProfileDTO editGetUserDetails(Long userId);
}