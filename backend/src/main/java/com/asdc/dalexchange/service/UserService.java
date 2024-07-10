package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserService {

    long newCustomers();
    double customersChange();
    LocalDateTime getCurrentDateTime();

    // View User Details
    Optional<User> viewUserDetails(long userId);

    // Edit User Details
    User editUserDetails(long userId, User updatedUserDetails);

    // Activate User Account
    User activateUser(long userId);

    // Deactivate User Account
    User deactivateUser(long userId);

    // Delete User Account
    void deleteUser(long userId);

    // Reset Password
    User resetPassword(long userId, String newPassword);
}
