package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AdminDashboardDTO;
import com.asdc.dalexchange.service.impl.AdminDashboardServiceImpl;
import com.asdc.dalexchange.service.impl.OrderServiceImpl;
import com.asdc.dalexchange.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardServiceImpl adminDashboardServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;


    @GetMapping("/stats")
    public AdminDashboardDTO getUsers(){
      return adminDashboardServiceImpl.adminStats();
    }

}
