package com.asdc.dalexchange.dto;


import com.asdc.dalexchange.enums.ProductCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedProductDTO {
    private String title;
    private double price;
    private String category;
    private ProductCondition productCondition;
    private String useDuration;
    private Integer quantityAvailable;
}