package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.PurchaseProductDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.OrderDetails;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PurchaseProductMapperImpl implements Mapper<OrderDetails, PurchaseProductDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseProductMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<OrderDetails, PurchaseProductDTO>() {
            @Override
            protected void configure() {
                map().setTitle(source.getProductId().getTitle());
                map().setCategory(source.getProductId().getCategory().getName());
            }
        });
    }

    @Override
    public PurchaseProductDTO mapTo(OrderDetails orderDetails) {
        return modelMapper.map(orderDetails, PurchaseProductDTO.class);
    }

    @Override
    public OrderDetails mapFrom(PurchaseProductDTO purchaseProductDTO) {
        throw new UnsupportedOperationException("Mapping from PurchaseProductDTO to OrderDetails is not supported.");
    }
}
