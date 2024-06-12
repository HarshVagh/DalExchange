package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.model.Product;
import lombok.*;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductCategoryDTO {
    private Long categoryId;
    private String name;
    private String description;
    private Set<Product> products;

}
