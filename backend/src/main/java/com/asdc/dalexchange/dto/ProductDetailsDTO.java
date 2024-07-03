package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.model.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDetailsDTO {

 private Long productId;
 // public User seller;
 private String title;
 private String description;
 private double price;
 // private ProductCategory category;
 private String category;
 private ProductCondition productCondition;
 private String useDuration;
 private ShippingType shippingType;
 private Integer quantityAvailable;



 //images
 private List<String> imageurl;

 //whishlist
 private  boolean  Favorite;

 //seller
 private String SellerName;
 private int Rating;
 private LocalDateTime sellerJoiningDate;

}
