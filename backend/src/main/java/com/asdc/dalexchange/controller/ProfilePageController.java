package com.asdc.dalexchange.controller;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.service.ProfilePageService;
import com.asdc.dalexchange.service.SoldItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfilePageController {

    @Autowired
    private ProfilePageService profilePageService;

    @Autowired
    private ProductRatingService productRatingService;

    @Autowired
    private SoldItemService soldItemService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @GetMapping("")
    public ResponseEntity<EditProfileDTO> profileDetails() {
        long userId = 1L; // TODO: Replace with current loggdin userid
        EditProfileDTO updatedUser = profilePageService.editGetUserDetails(userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/saved_products")
    public ResponseEntity<List<SavedProductDTO>> getAllSavedProducts() {
        long userId = 1L; // TODO: Replace with current loggdin userid
        List<SavedProductDTO> savedProductDTOs = productWishlistService.getAllSavedProducts(userId);
        return ResponseEntity.ok(savedProductDTOs);
    }

    @GetMapping("/sold_products")
    public ResponseEntity<List<SoldItemDTO>> getAllSoldProducts() {
        long userId = 1L; // TODO: Replace with current loggdin userid
        List<SoldItemDTO> soldProductDTOs = soldItemService.GetallSoldProduct(userId);
        return ResponseEntity.ok(soldProductDTOs);
    }

    @GetMapping("/purchased_products")
    public ResponseEntity<List<PurchaseProductDTO>> getAllPurchasedProducts() {
        long userId = 1L; // TODO: Replace with current loggdin userid
        List<PurchaseProductDTO> purchasedProductDTOs = productWishlistService.getAllPurchasedProduct(userId);
        return ResponseEntity.ok(purchasedProductDTOs);
    }

    @GetMapping("/product_ratings")
    public ResponseEntity<List<ProductRatingDTO>> getAllProductRatings() {
        long userId = 1L; // TODO: Replace with current loggdin userid
        List<ProductRatingDTO> productRatingDTOs = productRatingService.allReviewOfAllSoldItemsOfUser(userId);
        return ResponseEntity.ok(productRatingDTOs);
    }

    @PutMapping("/edit_user")
    public ResponseEntity<EditProfileDTO> editUserDetails(@RequestBody EditProfileDTO editProfileDTO) {
        long userId = 1L; // TODO: Replace with current loggdin userid
        EditProfileDTO updatedUser = profilePageService.editUserDetails(userId,editProfileDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/remove_saved/{productId}")
    public ResponseEntity<Object> unmarkAsFavorite(@PathVariable long productId) {
        long userId = 1L; // TODO: Replace with current loggdin userid
        boolean isUpdated = productWishlistService.markProductAsFavorite(productId);
       /* if (isUpdated) {*/
            return ResponseEntity.ok("Product removed successfully");
        /*}
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();*/
    }

}
