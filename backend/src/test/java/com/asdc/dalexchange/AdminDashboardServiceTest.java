package com.asdc.dalexchange;

import com.asdc.dalexchange.dto.AdminDashboardDTO;
import com.asdc.dalexchange.service.AdminDashboardService;
import com.asdc.dalexchange.service.OrderService;
import com.asdc.dalexchange.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdminDashboardServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AdminDashboardService adminDashboardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdminStats() {
        when(userService.newCustomers()).thenReturn(10L);
        when(orderService.newOrders()).thenReturn(20L);
        when(orderService.totalSales()).thenReturn(5000.0);
        when(orderService.avgSales()).thenReturn(250.0);
        when(orderService.salesChange()).thenReturn(15.2);

        AdminDashboardDTO adminStats = adminDashboardService.adminStats();

        assertEquals(10L, adminStats.getCustomers());
        assertEquals(20L, adminStats.getOrders());
        assertEquals(5000.0, adminStats.getSales());
        assertEquals(250.0, adminStats.getAvgOrderValue());
        assertEquals(15.2, adminStats.getSalesChange());
    }
}
