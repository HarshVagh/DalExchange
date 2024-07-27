package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.service.ProfilePageService;
import com.asdc.dalexchange.service.SoldItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Slf4j
public class ProfilePageController {

    private final ProfilePageService profilePageService;
    private final ProductRatingService productRatingService;
    private final SoldItemService soldItemService;
    private final ProductWishlistService productWishlistService;

    @GetMapping("")
    public ResponseEntity<EditProfileDTO> profileDetails() {
        log.info("Fetching profile details");
        EditProfileDTO updatedUser = profilePageService.editGetUserDetails();
        log.info("Profile details fetched successfully");
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/saved_products")
    public ResponseEntity<List<SavedProductDTO>> getAllSavedProducts() {
        log.info("Fetching all saved products");
        List<SavedProductDTO> savedProductDTOs = productWishlistService.getAllSavedProducts();
        log.info("Fetched {} saved products", savedProductDTOs.size());
        return ResponseEntity.ok(savedProductDTOs);
    }

    @GetMapping("/sold_products")
    public ResponseEntity<List<SoldItemDTO>> getAllSoldProducts() {
        log.info("Fetching all sold products");
        List<SoldItemDTO> soldProductDTOs = soldItemService.GetAllSoldProduct();
        log.info("Fetched {} sold products", soldProductDTOs.size());
        return ResponseEntity.ok(soldProductDTOs);
    }

    @GetMapping("/purchased_products")
    public ResponseEntity<List<PurchaseProductDTO>> getAllPurchasedProducts() {
        log.info("Fetching all purchased products");
        List<PurchaseProductDTO> purchasedProductDTOs = productWishlistService.getAllPurchasedProduct();
        log.info("Fetched {} purchased products", purchasedProductDTOs.size());
        return ResponseEntity.ok(purchasedProductDTOs);
    }

    @GetMapping("/product_ratings")
    public ResponseEntity<List<ProductRatingDTO>> getAllProductRatings() {
        log.info("Fetching all product ratings");
        List<ProductRatingDTO> productRatingDTOs = productRatingService.allReviewOfAllSoldItemsOfUser();
        log.info("Fetched {} product ratings", productRatingDTOs.size());
        return ResponseEntity.ok(productRatingDTOs);
    }

    @PutMapping("/edit_user")
    public ResponseEntity<EditProfileDTO> editUserDetails(@RequestBody EditProfileDTO editProfileDTO) {
        log.info("Updating user details for userId: {}", editProfileDTO.getUserId());
        EditProfileDTO updatedUser = profilePageService.editUserDetails(editProfileDTO);
        log.info("User details updated successfully for userId: {}", editProfileDTO.getUserId());
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/remove_saved/{productId}")
    public ResponseEntity<Object> unmarkAsFavorite(@PathVariable long productId) {
        log.info("Unmarking productId: {} as favorite", productId);
        boolean isUpdated = productWishlistService.markProductAsFavorite(productId);
        if (isUpdated) {
            log.info("ProductId: {} removed from favorites successfully", productId);
            return ResponseEntity.ok("Product removed successfully");
        } else {
            log.info("ProductId: {} was not in favorites", productId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in favorites");
        }
    }
}
