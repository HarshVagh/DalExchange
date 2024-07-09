package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.model.User;

import java.util.List;

public interface ProfilePageService {

    //home page ddetails of the user profilepage
    ProfilePageDTO ProfileDetails(Long userid);

    // get the all saved items
    List<SavedProductDTO> GetAllsavedProduct(Long userid);

    //get the all sold itemsby the given user
    List<SoldItemDTO> GetallSoldProduct(Long userid);

    //get the all purchase the history
    List<PurchaseProductDTO> GetallPurchasedProduct(Long userid);

    // get the review of the product rating  and anthe review of their profile
    List<ProductRatingDTO> GetAllProductRating(Long userid);

    // edit the profile of user details
    EditProfileDTO editUserDetails(Long userId , EditProfileDTO editProfileDTO);

    EditProfileDTO editGetUserDetails(Long userId);
}