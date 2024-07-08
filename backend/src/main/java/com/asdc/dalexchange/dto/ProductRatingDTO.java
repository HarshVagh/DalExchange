package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductRatingID;
import com.asdc.dalexchange.model.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRatingDTO {

    //private Product productId;
    private String title;
    private String review;
    private Integer rating;

}
