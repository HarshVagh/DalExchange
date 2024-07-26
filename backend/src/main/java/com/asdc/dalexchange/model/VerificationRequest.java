package com.asdc.dalexchange.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerificationRequest {
    // Getters and setters
    private String email;
    private String code;

}
