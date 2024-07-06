package com.asdc.dalexchange.service;


import com.asdc.dalexchange.dto.AdminDashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final OrderService orderService;

    public AdminDashboardService(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    public AdminDashboardDTO adminStats(){
        AdminDashboardDTO adminStats = new AdminDashboardDTO();
        adminStats.setCustomers(userService.newCustomers());
        adminStats.setOrders(orderService.newOrders());
        adminStats.setSales(orderService.totalSales());
        adminStats.setAvgOrderValue(orderService.avgSales());
        adminStats.setSalesChange(orderService.salesChange());
        adminStats.setOrdersChange(orderService.ordersChange());
        adminStats.setCustomersChange(userService.customersChange());
        adminStats.setAvgOrderValueChange(orderService.avgOrderValueChange());
        return adminStats;
    }



}
