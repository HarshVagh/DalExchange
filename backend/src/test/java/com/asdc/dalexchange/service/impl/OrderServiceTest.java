package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Spy
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalesChange() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 7, 1, 0, 0);
        doReturn(fixedDateTime).when(orderService).getCurrentDateTime();

        LocalDateTime startOfCurrentPeriod = fixedDateTime.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        when(orderRepository.getTotalSalesSince(startOfCurrentPeriod)).thenReturn(200.0);
        when(orderRepository.getTotalSalesBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(100.0);

        double percentageChange = orderService.salesChange();
        assertEquals(100.0, percentageChange, 0.01);
    }

    @Test
    void testOrdersChange() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 7, 1, 0, 0);
        doReturn(fixedDateTime).when(orderService).getCurrentDateTime();

        LocalDateTime startOfCurrentPeriod = fixedDateTime.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        when(orderRepository.countOrdersSince(startOfCurrentPeriod)).thenReturn(20L);
        when(orderRepository.countOrdersBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(10L);

        double percentageChange = orderService.ordersChange();
        assertEquals(100.0, percentageChange, 0.01);
    }

    @Test
    void testAvgOrderValueChange() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 7, 1, 0, 0);
        doReturn(fixedDateTime).when(orderService).getCurrentDateTime();

        LocalDateTime startOfCurrentPeriod = fixedDateTime.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        when(orderRepository.getAvgOrderValueSince(startOfCurrentPeriod)).thenReturn(50.0);
        when(orderRepository.getAvgOrderValueBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(25.0);

        double percentageChange = orderService.avgOrderValueChange();
        assertEquals(100.0, percentageChange, 0.01);
    }

    @Test
    void testNewOrders() {
        when(orderRepository.countOrdersInLast30Days()).thenReturn(30L);
        long newOrders = orderService.newOrders();
        assertEquals(30L, newOrders);
    }

    @Test
    void testTotalSales() {
        when(orderRepository.totalSalesInLast30Days()).thenReturn(300.0);
        double totalSales = orderService.totalSales();
        assertEquals(300.0, totalSales, 0.01);
    }

    @Test
    void testAvgSales() {
        when(orderRepository.avgOrderValueInLast30Days()).thenReturn(10.0);
        double avgSales = orderService.avgSales();
        assertEquals(10.0, avgSales, 0.01);
    }

    // Mocking getCurrentDateTime method
    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
