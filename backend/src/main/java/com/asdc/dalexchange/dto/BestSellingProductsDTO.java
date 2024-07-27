package com.asdc.dalexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BestSellingProductsDTO {
    private String productName;
    private int itemsSold;
}