package com.asdc.dalexchange.controller;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.service.ProfilePageService;
import com.asdc.dalexchange.service.SoldItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProfilePageController {

    @Autowired
    private ProfilePageService profilePageService;

    @Autowired
    private SoldItemService soldItemService;

    @GetMapping("/{userId}/profiledetails")
    public ResponseEntity<ProfilePageDTO> profilledetails(@PathVariable Long userId) {
        ProfilePageDTO ProfilePageDTOs = profilePageService.ProfileDetails(userId);
        return ResponseEntity.ok(ProfilePageDTOs);
    }

    @GetMapping("/saved_products/{userId}")
    public ResponseEntity<List<SavedProductDTO>> getAllSavedProducts(@PathVariable Long userId) {
        List<SavedProductDTO> savedProductDTOs = profilePageService.GetAllsavedProduct(userId);
        return ResponseEntity.ok(savedProductDTOs);
    }

    @PutMapping("/saved_products/{userId}/remove_product/{productId}")
    public ResponseEntity<List<SavedProductDTO>> removesavedproduct(@PathVariable Long userId,@PathVariable Long productId) {
        List<SavedProductDTO> savedProductDTOs = profilePageService.GetAllsavedProduct(userId);
        return ResponseEntity.ok(savedProductDTOs);
    }


    @GetMapping("/sold_products/{userId}")
    public ResponseEntity<List<SoldItemDTO>> getAllSoldProducts(@PathVariable Long userId) {
        List<SoldItemDTO> soldProductDTOs = soldItemService.GetallSoldProduct(userId);
        return ResponseEntity.ok(soldProductDTOs);
    }

    @GetMapping("/purchased_products/{userId}")
    public ResponseEntity<List<PurchaseProductDTO>> getAllPurchasedProducts(@PathVariable Long userId) {
        List<PurchaseProductDTO> purchasedProductDTOs = profilePageService.GetallPurchasedProduct(userId);
        return ResponseEntity.ok(purchasedProductDTOs);
    }

    @GetMapping("/product_ratings/{userId}")
    public ResponseEntity<List<ProductRatingDTO>> getAllProductRatings(@PathVariable Long userId) {
        List<ProductRatingDTO> productRatingDTOs = profilePageService.GetAllProductRating(userId);
        return ResponseEntity.ok(productRatingDTOs);
    }

    @PutMapping("/edit_user/{userId}")
    public ResponseEntity<String> editUserDetails(@RequestBody EditProfileDTO editProfileDTO, @PathVariable Long userId) {
        EditProfileDTO updatedUser = profilePageService.editUserDetails(userId,editProfileDTO);
        return ResponseEntity.ok().body("Profile Edited Sucessfully");
    }

    @GetMapping("/edit_user/{userId}")
    public ResponseEntity<EditProfileDTO> editUserDetails(@PathVariable long userId) {
        EditProfileDTO updatedUser = profilePageService.editGetUserDetails(userId);
        return ResponseEntity.ok(updatedUser);
    }

}
