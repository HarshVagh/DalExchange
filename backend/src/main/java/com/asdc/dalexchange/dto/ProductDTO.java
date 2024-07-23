package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {
    public Long productId;
    public User seller;
    public String title;
    public String description;
    public double price;
    public ProductCategory category;
    public ProductCondition productCondition;
    public String useDuration;
    public ShippingType shippingType;
    public Integer quantityAvailable;
    public LocalDateTime createdAt;


}
