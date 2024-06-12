package com.asdc.dalexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfoDTO {
    private String sellerName;
    private double sellerRating;
    private LocalDate sellerJoiningDate;

}