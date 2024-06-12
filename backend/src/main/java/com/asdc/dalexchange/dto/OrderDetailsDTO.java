package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderDetailsDTO {
    private Integer buyerId;
    private User buyer;
    private Product productId;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime transactionDatetime;

}
