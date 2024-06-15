package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.dto.SellerInfoDTO;
import com.asdc.dalexchange.model.Seller;
import com.asdc.dalexchange.repository.SellerRepository;
import com.asdc.dalexchange.service.SellerService;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SellerServiceImp implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ModelMapper modelMapper;

    // get the all sold items of the given userid
/*
    @Override
    public List<ProductDTO> soldItems(Long userId) {
        // Fetch sold items by user ID
        List<Seller> allSoldItems = this.sellerRepository.findByUserUserId(userId);

        // Convert to ProductDTO using ModelMapper
        List<ProductDTO> productDTOs = allSoldItems.stream()
                .map(seller -> modelMapper.map(seller.getProduct(), ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOs;
    }
*/



    // get the seller information with the seller name with rating and created account time(product page)
    @Override
        public SellerDTO getSellerInfo(long userId) {
          /*  Seller seller = this.sellerRepository.findByUserUserId(userId).stream().findFirst().orElse(() -> new ResourceNotFoundException("User is not Found od the given userId " + userId));
            if (seller == null) {
                throw new RuntimeException("Seller not found for user ID: " + userId);
            }


            SellerDTO sellerDTO = modelMapper.map(seller,SellerDTO.class);

            return sellerDTO;*/

            Seller seller = this.sellerRepository.findByUserUserId(userId)
                    .stream().findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Seller not found for user ID: " + userId));

            SellerDTO sellerDTO = modelMapper.map(seller, SellerDTO.class);
            return sellerDTO;

}}
