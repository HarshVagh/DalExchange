package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProfilePageDTO;
import com.asdc.dalexchange.dto.SavedProductDTO;
import com.asdc.dalexchange.dto.SoldItemDTO;
import com.asdc.dalexchange.model.User;

import java.util.List;

public interface ProfilePageService {

    //home page ddetails of the user profilepage
    ProfilePageDTO ProfileDetails(Long userid);

    // get the all saved items
    List<SavedProductDTO> GetAllsavedProduct(Long userid);

    //get the all sold itemsby the given user
    List<SoldItemDTO> GetallSoldProduct(Long userid);

}