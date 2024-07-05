package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.SellerDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SellerService {

    public Optional<SellerDTO> getSellerById(Long sellerId);

}

