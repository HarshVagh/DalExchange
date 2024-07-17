package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductModerationMapperImpl implements Mapper<Product, ProductModerationDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public ProductModerationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductModerationDTO mapTo(Product product) {
        return this.modelMapper.map(product, ProductModerationDTO.class);
    }

    @Override
    public Product mapFrom(ProductModerationDTO productModerationDTO) {
        return this.modelMapper.map(productModerationDTO, Product.class);
    }
}
