package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalesChange() {
        LocalDateTime now = LocalDateTime.now();
        when(orderRepository.getTotalSalesSince(any(LocalDateTime.class))).thenReturn(1000.0);
        when(orderRepository.getTotalSalesBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(800.0);

        double result = orderService.salesChange();

        assertEquals(25.0, result);

        // Additional checks to ensure line coverage for null values
        when(orderRepository.getTotalSalesSince(any(LocalDateTime.class))).thenReturn(null);
        when(orderRepository.getTotalSalesBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = orderService.salesChange();
        assertEquals(0.0, result);
    }

    @Test
    void testOrdersChange() {
        LocalDateTime now = LocalDateTime.now();
        when(orderRepository.countOrdersSince(any(LocalDateTime.class))).thenReturn(50L);
        when(orderRepository.countOrdersBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(40L);

        double result = orderService.ordersChange();

        assertEquals(25.0, result);

        // Additional checks to ensure line coverage for null values
        when(orderRepository.countOrdersSince(any(LocalDateTime.class))).thenReturn(null);
        when(orderRepository.countOrdersBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = orderService.ordersChange();
        assertEquals(0.0, result);
    }

    @Test
    void testAvgOrderValueChange() {
        LocalDateTime now = LocalDateTime.now();
        when(orderRepository.getAvgOrderValueSince(any(LocalDateTime.class))).thenReturn(20.0);
        when(orderRepository.getAvgOrderValueBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(16.0);

        double result = orderService.avgOrderValueChange();

        assertEquals(25.0, result);

        // Additional checks to ensure line coverage for null values
        when(orderRepository.getAvgOrderValueSince(any(LocalDateTime.class))).thenReturn(null);
        when(orderRepository.getAvgOrderValueBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = orderService.avgOrderValueChange();
        assertEquals(0.0, result);
    }

    @Test
    void testNewOrders() {
        when(orderRepository.countOrdersInLast30Days()).thenReturn(100L);

        long result = orderService.newOrders();

        assertEquals(100, result);
    }

    @Test
    void testTotalSales() {
        when(orderRepository.totalSalesInLast30Days()).thenReturn(5000.0);

        double result = orderService.totalSales();

        assertEquals(5000.0, result);
    }

    @Test
    void testAvgSales() {
        when(orderRepository.avgOrderValueInLast30Days()).thenReturn(50.0);

        double result = orderService.avgSales();

        assertEquals(50.0, result);
    }

    @Test
    void testGetOrderById() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(1L);
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderDetails));

        OrderDetails result = orderService.getOrderById(1);

        assertEquals(orderDetails, result);

        // Test the case where order is not found
        when(orderRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(2);
        });

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    void testUpdateOrder() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(1L);
        orderDetails.setTotalAmount(100.0);
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderDetails));
        when(orderRepository.save(any(OrderDetails.class))).thenReturn(orderDetails);

        OrderDetails updatedOrderDetails = new OrderDetails();
        updatedOrderDetails.setTotalAmount(150.0);
        updatedOrderDetails.setOrderStatus(OrderStatus.SHIPPED);
        updatedOrderDetails.setAdminComments("Updated comment");

        OrderDetails result = orderService.updateOrder(1, updatedOrderDetails);

        assertEquals(150.0, result.getTotalAmount());
        assertEquals(OrderStatus.SHIPPED, result.getOrderStatus());
        assertEquals("Updated comment", result.getAdminComments());

        // Test the case where order is not found
        when(orderRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(2, updatedOrderDetails);
        });

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    void testCancelOrder() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(1L);
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderDetails));
        when(orderRepository.save(any(OrderDetails.class))).thenReturn(orderDetails);

        orderService.cancelOrder(1, "Admin comment");

        assertEquals(OrderStatus.CANCELLED, orderDetails.getOrderStatus());
        assertEquals("Admin comment", orderDetails.getAdminComments());

        // Test the case where order is not found
        when(orderRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.cancelOrder(2, "Admin comment");
        });

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    void testProcessRefund() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(1L);
        orderDetails.setTotalAmount(200.0);
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderDetails));
        when(orderRepository.save(any(OrderDetails.class))).thenReturn(orderDetails);

        OrderDetails result = orderService.processRefund(1, 50.0);

        assertEquals(150.0, result.getTotalAmount());

        // Test the case where order is not found
        when(orderRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.processRefund(2, 50.0);
        });

        assertEquals("Order not found", exception.getMessage());
    }
}
