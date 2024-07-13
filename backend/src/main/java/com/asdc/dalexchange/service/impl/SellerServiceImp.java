package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.mappers.impl.SellerDetailsMapperImpl;
import com.asdc.dalexchange.model.Seller;
import com.asdc.dalexchange.repository.SellerRepository;
import com.asdc.dalexchange.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImp implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerDetailsMapperImpl sellerDetailsMapper;

    @Autowired
    public SellerServiceImp(SellerRepository sellerRepository, SellerDetailsMapperImpl sellerDetailsMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerDetailsMapper = sellerDetailsMapper;
    }

    @Override
    public Optional<SellerDTO> getSellerById(Long sellerId) {
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);
        if (sellerOptional.isPresent()) {
            SellerDTO sellerDTO = sellerDetailsMapper.mapTo(sellerOptional.get());
            return Optional.of(sellerDTO);
        }
        return Optional.empty();
    }
}
