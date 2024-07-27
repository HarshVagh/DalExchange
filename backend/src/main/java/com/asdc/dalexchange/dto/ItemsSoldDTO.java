package com.asdc.dalexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ItemsSoldDTO {
    private String month;
    private int itemsSold;

}
