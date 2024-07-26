package com.asdc.dalexchange.service.impl;


import com.asdc.dalexchange.dto.PaymentDTO;
import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.enums.PaymentStatus;
import com.asdc.dalexchange.mappers.impl.PaymentMapperImpl;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.Payment;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.PaymentRepository;
import com.asdc.dalexchange.service.PaymentService;
import com.asdc.dalexchange.service.TradeRequestService;
import com.asdc.dalexchange.specifications.PaymentSpecification;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Autowired
    private PaymentMapperImpl paymentMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TradeRequestService tradeRequestService;

    @Value("${frontend.endpoint}")
    private String frontendURL;

    @Override
    public String createPaymentIntent( Map<String,Object> requestBody) {
        try {
            Long productId = Long.parseLong(requestBody.get("productId").toString());
            Long orderId = Long.parseLong(requestBody.get("orderId").toString());
            double productPrice = tradeRequestService.getApprovedTradeRequestAmount(productId);
            String successURL = frontendURL + "/payment/success?amount=" + productPrice
                   + "&productId=" + productId + "&paymentIntentId={CHECKOUT_SESSION_ID}" + "&orderId=" + orderId;

            String failureURL = frontendURL + "/payment/fail";

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
    public PaymentDTO savePayment(Map<String, Object> requestBody) {
        Long orderId = Long.parseLong(requestBody.get("orderId").toString());
        System.out.println("the order is :" + orderId);
        // Retrieve the order
        Optional<OrderDetails> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new ResourceNotFoundException("Order not found");
        }

        OrderDetails order = orderOptional.get();
        order.setOrderStatus(OrderStatus.Delivered);
        orderRepository.save(order);

        // Retrieve payments
        Specification<Payment> spec = PaymentSpecification.hasProductId(orderId);
        List<Payment> payments = paymentRepository.findAll(spec);
        if (payments.isEmpty()) {
            return new PaymentDTO(); // Or throw an exception if required
        }

        Payment payment = payments.get(0);
        payment.setPaymentStatus(PaymentStatus.completed);
        payment.setPaymentDate(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.mapTo(savedPayment);
    }
}
