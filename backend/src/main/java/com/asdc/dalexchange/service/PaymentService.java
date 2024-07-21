package com.asdc.dalexchange.service;

import java.security.Principal;

public interface PaymentService {
    public String createPaymentIntent(String amount, Long productId, Long userId, Principal principal);
}
