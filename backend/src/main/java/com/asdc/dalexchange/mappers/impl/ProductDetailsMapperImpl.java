package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailsMapperImpl implements Mapper<Product, ProductDetailsDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductDetailsMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDetailsDTO mapTo(Product product) {
        ProductDetailsDTO productDetailsDTO = modelMapper.map(product, ProductDetailsDTO.class);
        productDetailsDTO.setSellerJoiningDate(product.getSeller().getJoinedAt());
        if (product.getCategory() != null) {
            productDetailsDTO.setCategory(product.getCategory().getName());
        }
        return productDetailsDTO;
    }

    @Override
    public Product mapFrom(ProductDetailsDTO productDetailsDTO) {
        throw new UnsupportedOperationException("Mapping from ProductDetailsDTO to Product is not supported.");
    }
}
