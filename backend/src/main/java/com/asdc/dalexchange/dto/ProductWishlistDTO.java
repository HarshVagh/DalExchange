package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWishlistDTO {
    //private Long wishlistId;
    private long userId;
    private long productId;
}
