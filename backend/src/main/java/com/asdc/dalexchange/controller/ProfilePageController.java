package com.asdc.dalexchange.controller;


import com.asdc.dalexchange.dto.ProfilePageDTO;
import com.asdc.dalexchange.dto.SavedProductDTO;
import com.asdc.dalexchange.service.ProfilePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProfilePageController {

    @Autowired
    private ProfilePageService profilePageService;

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
}
