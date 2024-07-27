package com.asdc.dalexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TopSellingCategoriesDTO {
    private String category;
    private int sales;
}
