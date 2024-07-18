package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.enums.ProductCondition;
import com.asdc.dalexchange.enums.ShippingType;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDTO {
    private String title;
    private String description;
    private double price;
    private Long categoryId;
    private ProductCondition productCondition;
    private String useDuration;
    private ShippingType shippingType;
    private Integer quantityAvailable;
}
