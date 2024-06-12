package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.User;
import lombok.*;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SoldItemDTO {
    private Long soldItemId;
    private User seller;
    private Product product;
    private LocalDateTime soldDate;

}
