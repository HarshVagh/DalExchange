package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.SavedProductDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SavedProductMapperImpl implements Mapper<Product, SavedProductDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public SavedProductMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<Product, SavedProductDTO>() {
            @Override
            protected void configure() {
                map().setTitle(source.getTitle());
                map().setPrice(source.getPrice());
                map().setCategory(source.getCategory().getName());
                map().setProductCondition(source.getProductCondition());
                map().setUseDuration(source.getUseDuration());
                map().setQuantityAvailable(source.getQuantityAvailable());
            }
        });
    }

    @Override
    public SavedProductDTO mapTo(Product product) {
        return modelMapper.map(product, SavedProductDTO.class);
    }

    @Override
    public Product mapFrom(SavedProductDTO savedProductDTO) {
        throw new UnsupportedOperationException("Mapping from SavedProductDTO to Product is not supported.");
    }
}
