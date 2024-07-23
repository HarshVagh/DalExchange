package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.Payment;

public interface PaymentService {
         String createPaymentIntent(Long productId);
         void savePayment(String amount, Long productId, String paymentIntentId);
         Payment savePayment(Payment payment);
}
