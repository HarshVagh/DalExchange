package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.enums.PaymentStatus;
import com.asdc.dalexchange.model.OrderDetails;
import lombok.*;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaymentDTO {
    private Long paymentId;
    private OrderDetails order;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
    private Double amount;

}
