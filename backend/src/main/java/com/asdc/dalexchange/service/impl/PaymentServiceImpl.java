package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${api.endpoint}")
    private String APIendpoint;

    @Value("${frontend.endpoint}")
    private String frontendEndpoint;
    @Override
    public String createPaymentIntent(String amount, Long productId, Long userId, Principal principal) {
        try {
            // Format the success URL with productId, userId, and amount
            String successURL = APIendpoint + "/payment/success?amount=" + Double.parseDouble(amount)
                    + "&productId=" + productId
                    + "&userId=" + userId;

            String failureURL = frontendEndpoint + "/payment/fail";

            Stripe.apiKey = stripeSecretKey;

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCancelUrl(failureURL)
                    .setSuccessUrl(successURL)
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("cad")
                                            .setUnitAmount((long) Double.parseDouble(amount) * 100)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Dal Exchange")
                                                            .build())
                                            .build())
                            .build())
                    .build();

            Session s = Session.create(params);
            return s.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
