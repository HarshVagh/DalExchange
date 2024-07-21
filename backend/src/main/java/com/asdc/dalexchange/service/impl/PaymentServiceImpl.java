package com.asdc.dalexchange.service.impl;


import com.asdc.dalexchange.enums.PaymentStatus;
import com.asdc.dalexchange.model.Payment;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.PaymentRepository;
import com.asdc.dalexchange.repository.ShippingRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.PaymentService;
import com.asdc.dalexchange.util.AuthUtil;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    public String createPaymentIntent(String amount, Long productId, Principal principal) {
        try {
            Long userId = AuthUtil.getCurrentUserId(userRepository);
            // Assuming APIendpoint is the backend endpoint and frontendEndpoint is the frontend endpoint
            String successURL = APIendpoint + "/payment/success?amount=" + Double.parseDouble(amount)
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


    @Override
    public void savePayment(String amount, Long productId, String paymentIntentId, Principal principal) {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        // save the the order in orede repository

        //shippingRepository.save("Rutvik","Canada","6969","BaayersRoad","Halifax","Nova Scotia","B3L4P3");
        //orderRepository.save(userId,productId, amount, OrderStatus.PENDING,LocalDateTime.now(),shippingRepository.findById(1L),1L,"hello");


        Payment payment = new Payment();
        payment.setPaymentMethod("CARD"); // Assuming CARD for now, you can adjust based on actual payment method
        payment.setPaymentStatus(PaymentStatus.completed); // Assuming payment success, can be adjusted based on actual status
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(Double.parseDouble(amount));

        // Fetch and set the order details
       // OrderDetails order = orderRepository.findByProductIdAndBuyerUsername(productId, principal.getName());
       // payment.setOrder(order);
        payment.setOrder(orderRepository.getReferenceById(1));  //TODO : set the orderid

        paymentRepository.save(payment);
    }
}
