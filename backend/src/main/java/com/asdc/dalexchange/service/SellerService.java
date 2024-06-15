package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.dto.SellerInfoDTO;

import java.util.List;

public interface SellerService {


    /*// get all the sold items of the given user
    List<ProductDTO> soldItems(Long userid);

    // get the rating of the given seller id
    List<ProductDTO> Review(Long userid)*/;

    //get the seller informationeller rating and joinddate
    public SellerDTO getSellerInfo(long userId);

    //
}
