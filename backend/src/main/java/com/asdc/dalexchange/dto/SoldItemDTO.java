package com.asdc.dalexchange.dto;
import lombok.*;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SoldItemDTO {
    private Long soldItemId;
    private String title;
    private Double price;
    private LocalDateTime soldDate;
}
