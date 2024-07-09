package com.asdc.dalexchange.mappers.impl;


import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements Mapper<Product, ProductDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO mapTo(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Product mapFrom(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
