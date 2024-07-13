package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.AdminDashboardDTO;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.AdminDashboardService;
import com.asdc.dalexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @Autowired
    private UserService userService;

    @GetMapping("/stats")
    public AdminDashboardDTO getUsers(){
      return adminDashboardService.adminStats();
    }

    // View User Details
    @GetMapping("/{id}")
    public Optional<User> viewUserDetails(@PathVariable int id) {
        return userService.viewUserDetails(id);
    }

    // Edit User Details
    @PutMapping("/{id}")
    public User editUserDetails(@PathVariable Long id, @RequestBody User user) {
        // Ensure the user ID matches the path variable
        if (!id.equals(user.getUserId())) {
            throw new IllegalArgumentException("User ID mismatch");
        }
        return userService.editUserDetails(user);
    }

    // Activate User Account
    @PutMapping("/{id}/activate")
    public User activateUser(@PathVariable int id) {
        return userService.activateUser(id);
    }

    // Deactivate User Account
    @PutMapping("/{id}/deactivate")
    public User deactivateUser(@PathVariable int id) {
        return userService.deactivateUser(id);
    }

    // Delete User Account
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    // Reset Password
    @PutMapping("/{id}/reset-password")
    public User resetPassword(@PathVariable int id, @RequestBody String newPassword) {
        return userService.resetPassword(id, newPassword);
    }
}
