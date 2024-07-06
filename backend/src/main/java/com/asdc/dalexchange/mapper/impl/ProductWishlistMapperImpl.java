package com.asdc.dalexchange.mapper.impl;

import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.mapper.Mapper;
import com.asdc.dalexchange.model.ProductWishlist;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductWishlistMapperImpl implements Mapper<ProductWishlist, ProductWishlistDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductWishlistMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductWishlistDTO mapTo(ProductWishlist productWishlist) {
        return modelMapper.map(productWishlist, ProductWishlistDTO.class);
    }

    @Override
    public ProductWishlist mapFrom(ProductWishlistDTO productWishlistDTO) {
        return modelMapper.map(productWishlistDTO, ProductWishlist.class);
    }
}

