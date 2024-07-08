package com.asdc.dalexchange.service.imp;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProfilePageServiceImp implements ProfilePageService {


    @Autowired
    public ProductWishlistService productWishlistService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ProductRatingService productRatingService;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public ProfilePageDTO ProfileDetails(Long userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
        ProfilePageDTO profilePageDTO = new ProfilePageDTO();
        modelMapper.map(user, profilePageDTO);
        return profilePageDTO;
    }
}
