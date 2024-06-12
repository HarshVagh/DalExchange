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
    private ProductRatingID id;
    private Product productId;
    private User user;
    private String review;
    private Integer rating;

}
