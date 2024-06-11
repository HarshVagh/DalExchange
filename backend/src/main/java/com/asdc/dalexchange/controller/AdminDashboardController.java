package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AdminDashboardDTO;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.AdminDashboardService;
import com.asdc.dalexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminDashboardController {


    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/adminDashboard")
    public AdminDashboardDTO getUsers(){
      return adminDashboardService.adminStats();
    }
}
