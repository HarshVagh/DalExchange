package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Seller;
import com.asdc.dalexchange.repository.SellerRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellerDetailsMapperImpl implements Mapper<Seller, SellerDTO> {

    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;

    @Autowired
    public SellerDetailsMapperImpl(ModelMapper modelMapper, SellerRepository sellerRepository) {
        this.modelMapper = modelMapper;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public SellerDTO mapTo(Seller seller) {
        SellerDTO sellerDTO = modelMapper.map(seller, SellerDTO.class);
        // Additional mappings if needed
        // e.g., sellerDetailsDTO.setSomeField(seller.getSomeField());
        return sellerDTO;
    }

    @Override
    public Seller mapFrom(SellerDTO sellerDetailsDTO) {
        Seller seller = modelMapper.map(sellerDetailsDTO, Seller.class);
        // Additional mappings if needed
        // e.g., seller.setSomeField(sellerDetailsDTO.getSomeField());
        return seller;
    }

    // Add any additional methods if needed to interact with the repository
}
