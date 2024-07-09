package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.SoldItemDTO;
import com.asdc.dalexchange.mapper.Mapper;
import com.asdc.dalexchange.model.SoldItem;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SoldItemMapperImpl implements Mapper<SoldItem, SoldItemDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public SoldItemMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<SoldItem, SoldItemDTO>() {
            @Override
            protected void configure() {
                map().setTitle(source.getProduct().getTitle());
                map().setPrice(source.getProduct().getPrice());
            }
        });
    }

    @Override
    public SoldItemDTO mapTo(SoldItem soldItem) {
        return modelMapper.map(soldItem, SoldItemDTO.class);
    }

    @Override
    public SoldItem mapFrom(SoldItemDTO soldItemDTO) {
        throw new UnsupportedOperationException("Mapping from SoldItemDTO to SoldItem is not supported.");
    }
}
