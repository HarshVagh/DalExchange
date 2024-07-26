package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.PaymentDTO;
import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.enums.PaymentStatus;
import com.asdc.dalexchange.mappers.impl.PaymentMapperImpl;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.Payment;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.PaymentRepository;
import com.asdc.dalexchange.service.TradeRequestService;
import com.asdc.dalexchange.specifications.PaymentSpecification;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentMapperImpl paymentMapper;


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private TradeRequestService tradeRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock Stripe Session
        Stripe.apiKey = "sk_test_123";
    }

    @Test
    void testCreatePaymentIntentSuccess() {
        try (MockedStatic<Session> mockedSession = Mockito.mockStatic(Session.class)) {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("productId", 1L);
            requestBody.put("orderId", 1L);

            when(tradeRequestService.getApprovedTradeRequestAmount(anyLong())).thenReturn(100.0);

            Session session = mock(Session.class);
            when(session.getId()).thenReturn("session_id");
            mockedSession.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(session);

            String sessionId = paymentService.createPaymentIntent(requestBody);

            assertEquals("session_id", sessionId);
            verify(tradeRequestService, times(1)).getApprovedTradeRequestAmount(anyLong());
        }
    }

    @Test
    void testCreatePaymentIntentFailure() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", 1L);
        requestBody.put("orderId", 1L);

        when(tradeRequestService.getApprovedTradeRequestAmount(anyLong())).thenThrow(new RuntimeException("Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> paymentService.createPaymentIntent(requestBody));
        assertEquals("Failed to create payment intent: Error", exception.getMessage());
    }

    @Test
    void testSavePaymentSuccess() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("orderId", 1L);

        OrderDetails order = new OrderDetails();
        order.setOrderStatus(OrderStatus.Delivered);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.completed);
        when(paymentRepository.findAll(any(Specification.class))).thenReturn(List.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        PaymentDTO paymentDTO = new PaymentDTO();
        when(paymentMapper.mapTo(any(Payment.class))).thenReturn(paymentDTO);

        PaymentDTO result = paymentService.savePayment(requestBody);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(anyLong());
        verify(paymentRepository, times(1)).findAll(any(Specification.class));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testSavePaymentWhenOrderDoesNotExist() {
        Long orderId = 1L;
        Map<String, Object> paymentData = new HashMap<>();
        paymentData.put("orderId", orderId);
        paymentData.put("amount", 100.0);
        paymentData.put("paymentMethod", "Credit Card");
        paymentData.put("status", "Completed");

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.savePayment(paymentData);
        });

        assertEquals("Order not found", exception.getMessage());
    }


    @Test
    public void testSavePayment_noPayments() {
        Long orderId = 1L;

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderStatus(OrderStatus.Delivered);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderDetails));

        Specification<Payment> spec = PaymentSpecification.hasProductId(orderId);
        when(paymentRepository.findAll(spec)).thenReturn(Collections.emptyList());

        PaymentDTO expectedPaymentDTO = new PaymentDTO();
        when(paymentMapper.mapTo(null)).thenReturn(expectedPaymentDTO);

        Map<String, Object> requestBody = Map.of("orderId", orderId);

        PaymentDTO result = paymentService.savePayment(requestBody);

        assertEquals(expectedPaymentDTO, result);
    }
}
