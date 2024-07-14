package com.asdc.dalexchange.service.impl;


import com.asdc.dalexchange.dto.AdminDashboardDTO;
import com.asdc.dalexchange.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private final UserServiceImpl userServiceImpl;

    @Autowired
    private final OrderServiceImpl orderServiceImpl;

    public AdminDashboardServiceImpl(UserServiceImpl userServiceImpl, OrderServiceImpl orderServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
    }

    public AdminDashboardDTO adminStats(){
        AdminDashboardDTO adminStats = new AdminDashboardDTO();
        adminStats.setCustomers(userServiceImpl.newCustomers());
        adminStats.setOrders(orderServiceImpl.newOrders());
        adminStats.setSales(orderServiceImpl.totalSales());
        adminStats.setAvgOrderValue(orderServiceImpl.avgSales());
        adminStats.setSalesChange(orderServiceImpl.salesChange());
        adminStats.setOrdersChange(orderServiceImpl.ordersChange());
        adminStats.setCustomersChange(userServiceImpl.customersChange());
        adminStats.setAvgOrderValueChange(orderServiceImpl.avgOrderValueChange());
        return adminStats;
    }





}
