package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.model.VerificationCode;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    public User registerUser(User user) {
        User registeredUser = userRepository.save(user);

        String verificationCode = generateVerificationCode();
        VerificationCode code = new VerificationCode();
        code.setEmail(user.getEmail());
        code.setCode(verificationCode);
        code.setExpiryDate(LocalDateTime.now().plusHours(1));
        verificationCodeRepository.save(code);

        String subject = "Verify your email";
        String text = "Your verification code is " + verificationCode;
        emailService.sendEmail(user.getEmail(), subject, text);

        return registeredUser;
    }

    public boolean verifyUser(String email, String code) {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findByEmailAndCode(email, code);
        return verificationCode.isPresent() && verificationCode.get().getExpiryDate().isAfter(LocalDateTime.now());
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    public double customersChange() {
        LocalDateTime now = getCurrentDateTime();
        LocalDateTime startOfCurrentPeriod = now.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalCustomersLast30Days = userRepository.countUsersJoinedSince(startOfCurrentPeriod);
        Long totalCustomersPrevious30Days = userRepository.countUsersJoinedBetween(startOfPreviousPeriod,
                startOfCurrentPeriod);

        if (totalCustomersLast30Days == null) {
            totalCustomersLast30Days = 0L;
        }
        if (totalCustomersPrevious30Days == null) {
            totalCustomersPrevious30Days = 0L;
        }

        return calculatePercentageIncrease(totalCustomersLast30Days.doubleValue(),
                totalCustomersPrevious30Days.doubleValue());
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    private double calculatePercentageIncrease(Double current, Double previous) {
        if (previous == 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        return ((current - previous) / previous) * 100;
    }

    // new
    // View User Details
    public Optional<User> viewUserDetails(long userId) {
        return userRepository.findById(userId);
    }

    // Edit User Details
    public User editUserDetails(User user) {
        return userRepository.save(user);
    }

    // Activate User Account
    public User activateUser(long userId) {
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
    public User deactivateUser(long userId) {
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
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    // Reset Password
    public User resetPassword(long userId, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public long newCustomers(){
        return userRepository.countUsersJoinedInLast30Days();
    }

}
