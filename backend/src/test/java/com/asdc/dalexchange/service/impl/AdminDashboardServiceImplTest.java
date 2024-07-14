package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.AdminDashboardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdminDashboardServiceServiceImplTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private OrderServiceImpl orderServiceImpl;

    @InjectMocks
    private AdminDashboardServiceServiceImpl adminDashboardServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdminStats() {
        when(userServiceImpl.newCustomers()).thenReturn(10L);
        when(orderServiceImpl.newOrders()).thenReturn(20L);
        when(orderServiceImpl.totalSales()).thenReturn(5000.0);
        when(orderServiceImpl.avgSales()).thenReturn(250.0);
        when(orderServiceImpl.salesChange()).thenReturn(15.2);

        AdminDashboardDTO adminStats = adminDashboardServiceImpl.adminStats();

        assertEquals(10L, adminStats.getCustomers());
        assertEquals(20L, adminStats.getOrders());
        assertEquals(5000.0, adminStats.getSales());
        assertEquals(250.0, adminStats.getAvgOrderValue());
        assertEquals(15.2, adminStats.getSalesChange());
    }
}
