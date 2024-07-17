package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {
    private Long productId;
    private String title;
    private String description;
    private double price;
    private String category;
    private ProductCondition productCondition;
    private String useDuration;
    private ShippingType shippingType;
    private Integer quantityAvailable;
    private List<String> imageurl;
    private  boolean  Favorite;
    private Long SellerId;
    private String SellerName;
    private double Rating;
    private LocalDateTime sellerJoiningDate;
}
