package com.asdc.dalexchange.mapper.impl;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.mapper.Mapper;
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
        // Additional mappings if needed
        productDetailsDTO.setSellerJoiningDate(product.getSeller().getJoinedAt());
        productDetailsDTO.setCategory(product.getCategory().getName());
        // You can add more mappings here
        return productDetailsDTO;
    }

    @Override
    public Product mapFrom(ProductDetailsDTO productDetailsDTO) {
        // Mapping from ProductDetailsDTO to Product is typically not needed for this use case
        throw new UnsupportedOperationException("Mapping from ProductDetailsDTO to Product is not supported.");
    }
}
