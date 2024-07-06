package com.asdc.dalexchange;

import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalesChangeNoSales() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfCurrentPeriod = now.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        when(orderRepository.getTotalSalesSince(startOfCurrentPeriod)).thenReturn(0.0);
        when(orderRepository.getTotalSalesBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(0.0);

        double percentageIncrease = orderService.salesChange();

        assertEquals(0.0, percentageIncrease, 0.01);
    }

    @Test
    public void testNewOrders() {
        when(orderRepository.countOrdersInLast30Days()).thenReturn(20L);
        long newOrders = orderService.newOrders();
        assertEquals(20L, newOrders);
    }

    @Test
    public void testTotalSales() {
        when(orderRepository.totalSalesInLast30Days()).thenReturn(5000.0);
        double totalSales = orderService.totalSales();
        assertEquals(5000.0, totalSales, 0.01);
    }

    @Test
    public void testAvgSales() {
        when(orderRepository.avgOrderValueInLast30Days()).thenReturn(250.0);
        double avgSales = orderService.avgSales();
        assertEquals(250.0, avgSales, 0.01);
    }

    @Test
    public void testOrdersChange_noChange() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalOrdersLast30Days = 100L;
        Long totalOrdersPrevious30Days = 100L;

        // Mocking behavior
        when(orderRepository.countOrdersSince(startOfCurrentPeriod)).thenReturn(totalOrdersLast30Days);
        when(orderRepository.countOrdersBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(totalOrdersPrevious30Days);

        // When
        double percentageChange = orderService.ordersChange();

        // Then
        assertEquals(0.0, percentageChange);
    }




    @Test
    public void testOrdersChange_noOrders() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalOrdersLast30Days = 0L;
        Long totalOrdersPrevious30Days = 0L;

        // Mocking behavior
        when(orderRepository.countOrdersSince(startOfCurrentPeriod)).thenReturn(totalOrdersLast30Days);
        when(orderRepository.countOrdersBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(totalOrdersPrevious30Days);

        // When
        double percentageChange = orderService.ordersChange();

        // Then
        assertEquals(0.0, percentageChange);
    }



    @Test
    public void testAvgOrderValueChange_noChange() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Double avgOrderValueLast30Days = 100.0;
        Double avgOrderValuePrevious30Days = 100.0;

        // Mocking behavior
        when(orderRepository.getAvgOrderValueSince(startOfCurrentPeriod)).thenReturn(avgOrderValueLast30Days);
        when(orderRepository.getAvgOrderValueBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(avgOrderValuePrevious30Days);

        // When
        double percentageChange = orderService.avgOrderValueChange();

        // Then
        assertEquals(0.0, percentageChange);
    }



    @Test
    public void testAvgOrderValueChange_noOrders() {
        // Given
        LocalDateTime startOfCurrentPeriod = LocalDateTime.now().minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Double avgOrderValueLast30Days = 0.0;
        Double avgOrderValuePrevious30Days = 0.0;

        // Mocking behavior
        when(orderRepository.getAvgOrderValueSince(startOfCurrentPeriod)).thenReturn(avgOrderValueLast30Days);
        when(orderRepository.getAvgOrderValueBetween(startOfPreviousPeriod, startOfCurrentPeriod)).thenReturn(avgOrderValuePrevious30Days);

        // When
        double percentageChange = orderService.avgOrderValueChange();

        // Then
        assertEquals(0.0, percentageChange);
    }
}

