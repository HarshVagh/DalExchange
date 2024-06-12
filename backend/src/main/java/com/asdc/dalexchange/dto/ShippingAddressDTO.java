package com.asdc.dalexchange.dto;

import com.asdc.dalexchange.model.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShippingAddressDTO {
    private Long addressId;
    private User user;
    private String billingName;
    private String country;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;

}
