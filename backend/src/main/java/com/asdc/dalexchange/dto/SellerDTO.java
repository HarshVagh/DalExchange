package com.asdc.dalexchange.dto;


import com.asdc.dalexchange.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SellerDTO {
    private Long sellerId;
    private User user;
    private double sellerRating;
    private LocalDateTime sellerJoiningDate;

}
