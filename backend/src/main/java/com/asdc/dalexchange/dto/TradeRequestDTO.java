package com.asdc.dalexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequestDTO {
    private Long requestId;

    private ProductListingDTO product;

    private String buyerName;
    private double buyerRating;
    private LocalDateTime buyerJoiningDate;

    private String sellerName;
    private double sellerRating;
    private LocalDateTime sellerJoiningDate;

    private double requestedPrice;
    private String requestStatus; // 'pending', 'approved', 'rejected'
    private LocalDateTime requestedAt;
}
