package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.Payment;

import java.security.Principal;

public interface PaymentService {
         String createPaymentIntent(String amount, Long productId, Principal principal);
         void savePayment(String amount, Long productId, String paymentIntentId, Principal principal);
          Payment savePayment(Payment payment);
}
