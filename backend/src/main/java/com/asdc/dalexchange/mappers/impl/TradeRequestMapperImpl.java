package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.TradeRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeRequestMapperImpl implements Mapper<TradeRequest, TradeRequestDTO> {

    @Autowired
    private ModelMapper modelMapper;
    private TypeMap<TradeRequest, TradeRequestDTO> propertyMapper;

    @Autowired
    private Mapper<Product, ProductListingDTO> productListingMapper;

    public TradeRequestMapperImpl(ModelMapper modelMapper, Mapper<Product, ProductListingDTO> productListingMapper) {
        this.modelMapper = modelMapper;
        modelMapper.typeMap(TradeRequest.class, TradeRequestDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getSeller().getFullName(), TradeRequestDTO::setSellerName);
            mapper.map(src -> src.getSeller().getProfilePicture(), TradeRequestDTO::setSellerImage);
            mapper.map(src -> src.getSeller().getJoinedAt(), TradeRequestDTO::setSellerJoiningDate);
            mapper.map(src -> src.getSeller().getSellerRating(), TradeRequestDTO::setSellerRating);

            mapper.map(src -> src.getBuyer().getFullName(), TradeRequestDTO::setBuyerName);
            mapper.map(src -> src.getBuyer().getProfilePicture(), TradeRequestDTO::setBuyerImage);
            mapper.map(src -> src.getBuyer().getJoinedAt(), TradeRequestDTO::setBuyerJoiningDate);
            mapper.map(src -> src.getBuyer().getSellerRating(), TradeRequestDTO::setBuyerRating);
        });
    }

    @Override
    public TradeRequestDTO mapTo(TradeRequest tradeRequest) {
        TradeRequestDTO tradeRequestDTO = modelMapper.map(tradeRequest, TradeRequestDTO.class);
        tradeRequestDTO.setProduct(productListingMapper.mapTo(tradeRequest.getProduct()));
        return tradeRequestDTO;
    }

    @Override
    public TradeRequest mapFrom(TradeRequestDTO tradeRequestDTO) {
        return this.modelMapper.map(tradeRequestDTO, TradeRequest.class);
    }
}
