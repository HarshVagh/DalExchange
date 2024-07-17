package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.ProfilePageDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfilePageMapperImpl implements Mapper<User, ProfilePageDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public ProfilePageMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfilePageDTO mapTo(User user) {
        return modelMapper.map(user, ProfilePageDTO.class);
    }

    @Override
    public User mapFrom(ProfilePageDTO profilePageDTO) {
        throw new UnsupportedOperationException("Mapping from ProfilePageDTO to User is not supported.");
    }
}
