package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.model.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductImageDTO {
    private long imageId;
    private Product product;
    private String imageUrl;

}
