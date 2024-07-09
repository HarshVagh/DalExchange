package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.EditProfileDTO;
import com.asdc.dalexchange.mapper.Mapper;
import com.asdc.dalexchange.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditProfileMapperImpl implements Mapper<User, EditProfileDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public EditProfileMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EditProfileDTO mapTo(User user) {
        return modelMapper.map(user, EditProfileDTO.class);
    }

    @Override
    public User mapFrom(EditProfileDTO editProfileDTO) {
        return modelMapper.map(editProfileDTO, User.class);
    }
}
