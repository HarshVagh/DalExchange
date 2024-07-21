package com.asdc.dalexchange.service;

import java.security.Principal;

public interface PaymentService {
    public String createPaymentIntent(String amount, Long productId, Principal principal);
    public void savePayment(String amount, Long productId, String paymentIntentId, Principal principal);
}
