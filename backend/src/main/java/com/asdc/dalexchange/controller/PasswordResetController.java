package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.entity.PasswordResetToken;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.PasswordResetTokenRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        passwordResetTokenRepository.save(resetToken);

        String resetUrl = "http://localhost:3000/reset-password?token=" + token + "&email=" + email;

        emailService.sendEmail(email, "Password Reset Request", "Click the link to reset your password: " + resetUrl);

        return "Password reset email sent.";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String token, @RequestParam String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (!resetToken.getEmail().equals(email)) {
            throw new RuntimeException("Invalid email address");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        System.out.println("Updated password for user: " + email); // Debug statement

        return "Password reset successfully.";
    }


}