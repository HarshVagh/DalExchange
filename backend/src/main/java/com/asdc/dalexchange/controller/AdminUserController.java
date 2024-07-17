package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.UserDTO;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    // View User Details
    @GetMapping("/{id}")
    public Optional<UserDTO> viewUserDetails(@PathVariable Long id) {
        return userService.viewUserDetails(id);
    }

    // Edit User Details
    @PutMapping("/{id}")
    public UserDTO editUserDetails(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.editUserDetails(id, userDTO);
    }

    // Activate User Account
    @PutMapping("/{id}/activate")
    public UserDTO activateUser(@PathVariable Long id) {
        return userService.activateUser(id);
    }

    // Deactivate User Account
    @PutMapping("/{id}/deactivate")
    public UserDTO deactivateUser(@PathVariable Long id) {
        return userService.deactivateUser(id);
    }

    // Delete User Account
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

//    // Reset Password
//    @PutMapping("/{id}/reset-password")
//    public User resetPassword(@PathVariable Long id, @RequestBody String newPassword) {
//        return userService.resetPassword(id, newPassword);
//    }
}
