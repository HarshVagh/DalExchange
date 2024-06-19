package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductListingMapperImpl implements Mapper<Product, ProductListingDTO> {

    @Autowired
    private ModelMapper modelMapper;
    private final TypeMap<Product, ProductListingDTO> propertyMapper;

    public ProductListingMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.propertyMapper = this.modelMapper.createTypeMap(Product.class, ProductListingDTO.class);
        this.propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getCategory().getName(), ProductListingDTO::setCategoryName)
        );
    }

    @Override
    public ProductListingDTO mapTo(Product product) {
        return this.modelMapper.map(product, ProductListingDTO.class);
    }

    @Override
    public Product mapFrom(ProductListingDTO productListingDTO) {
        return this.modelMapper.map(productListingDTO, Product.class);
    }
}
