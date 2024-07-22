package com.asdc.dalexchange.service.impl;


import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.enums.PaymentStatus;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.PaymentRepository;
import com.asdc.dalexchange.repository.ShippingRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.PaymentService;
import com.asdc.dalexchange.service.TradeRequestService;
import com.asdc.dalexchange.util.AuthUtil;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;


    @Autowired
    private UserRepository userRepository;

    @Value("${api.endpoint}")
    private String APIendpoint;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TradeRequestService tradeRequestService;

    //Long userId = AuthUtil.getCurrentUserId(userRepository);

    @Value("${frontend.endpoint}")
    private String frontendEndpoint;
  /*  @Override
    public String createPaymentIntent(String amount, Long productId, Principal principal) {
        try {
            // Format the success URL with productId, userId, and amount
            String successURL = APIendpoint + "/payment/success?amount=" + Double.parseDouble(amount)
                    + "&productId=" + productId;

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
    }*/

    @Override
    public String createPaymentIntent( Long productId) {
        try {

            Long userId = 1L;//AuthUtil.getCurrentUserId(userRepository);

            double productPrice = tradeRequestService.getApprovedTradeRequestAmount(productId);
            String successURL = APIendpoint + "/payment/success?amount=" + productPrice
                    + "&productId=" + productId + "&paymentIntentId={CHECKOUT_SESSION_ID}";

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
                                            .setUnitAmount((long) (productPrice * 100))
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Dal Exchange")
                                                            .build())
                                            .build())
                            .build())
                    .build();
            System.out.println("the param is " + params);
            Session s = Session.create(params);
            return s.getId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment intent: " + e.getMessage(), e);
        }
    }


    @Override
    public void savePayment(String amount, Long productId, String paymentIntentId) {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        User user = userRepository.findByUserId(userId);
        // Fetch or create ShippingAddress
        ShippingAddress shippingAddress = new ShippingAddress();
        // Set shipping address details here
        shippingRepository.save(shippingAddress);

        // Create and save Payment
        Payment payment = new Payment();
        payment.setPaymentMethod("Stripe"); // Example
        payment.setPaymentStatus(PaymentStatus.completed);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(Double.parseDouble(amount));
        paymentRepository.save(payment);

        // Create and save OrderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setBuyer(user); // Fetch user based on principal
        orderDetails.setProductId(new Product()); // Fetch product based on productId
        orderDetails.setTotalAmount(Double.parseDouble(amount));
        orderDetails.setOrderStatus(OrderStatus.PENDING); // Example status
        orderDetails.setTransactionDatetime(LocalDateTime.now());
        orderDetails.setShippingAddress(shippingAddress);
        orderDetails.setPayment(payment);
        orderRepository.save(orderDetails);

    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
