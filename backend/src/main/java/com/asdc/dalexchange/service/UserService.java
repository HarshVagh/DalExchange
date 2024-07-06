package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long newCustomers(){
        return userRepository.countUsersJoinedInLast30Days();
    }

    public double customersChange() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfCurrentPeriod = now.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalCustomersLast30Days = userRepository.countUsersJoinedSince(startOfCurrentPeriod);
        Long totalCustomersPrevious30Days = userRepository.countUsersJoinedBetween(startOfPreviousPeriod, startOfCurrentPeriod);

        if (totalCustomersLast30Days == null) {
            totalCustomersLast30Days = 0L;
        }
        if (totalCustomersPrevious30Days == null) {
            totalCustomersPrevious30Days = 0L;
        }

        return calculatePercentageIncrease(totalCustomersLast30Days.doubleValue(), totalCustomersPrevious30Days.doubleValue());
    }

    private double calculatePercentageIncrease(Double current, Double previous) {
        if (previous == 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        return ((current - previous) / previous) * 100;
    }

    //new
    // View User Details
    public Optional<User> viewUserDetails(int userId) {
        return userRepository.findById(userId);
    }

    // Edit User Details
    public User editUserDetails(User user) {
        return userRepository.save(user);
    }

    // Activate User Account
    public User activateUser(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(true);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Deactivate User Account
    public User deactivateUser(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(false);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Delete User Account
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    // Reset Password
    public User resetPassword(int userId, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

}
