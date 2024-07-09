package com.asdc.dalexchange.dto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    private Long sellerId;
    private double sellerRating;
    private LocalDateTime sellerJoiningDate;
}
