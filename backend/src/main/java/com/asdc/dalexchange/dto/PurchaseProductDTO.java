package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProductDTO {

    private String title;

    private String category;

    private double totalAmount;

    private OrderStatus orderStatus;

    private LocalDateTime transactionDatetime;
}
