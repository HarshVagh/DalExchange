    package com.asdc.dalexchange.dto;
    import lombok.*;

    import java.time.LocalDateTime;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Data
    public class SellerDTO {

        private Long sellerId;

        private double sellerRating;

        private LocalDateTime sellerJoiningDate;

    }
