package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AdminDashboardDTO;
import com.asdc.dalexchange.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/stats")
    public AdminDashboardDTO getUsers(){
      return adminDashboardService.adminStats();
    }

}
