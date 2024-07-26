package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AdminDashboardDTO;
import com.asdc.dalexchange.dto.BestSellingProductsDTO;
import com.asdc.dalexchange.dto.ItemsSoldDTO;
import com.asdc.dalexchange.dto.TopSellingCategoriesDTO;
import com.asdc.dalexchange.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/stats")
    public AdminDashboardDTO getUsers(){
      return adminDashboardService.adminStats();
    }

    @GetMapping("/items-sold")
    public List<ItemsSoldDTO> getItemsSold(){
        return adminDashboardService.getItemsSold();
    }

    @GetMapping("/top-selling-categories")
    public List<TopSellingCategoriesDTO> getTopSellingCategories(){
        return adminDashboardService.getTopSellingCategories();
    }

    @GetMapping("/best-selling-products")
    public List<BestSellingProductsDTO> getBestSellingProducts(){
        return adminDashboardService.getBestSellingProducts();
    }

}
