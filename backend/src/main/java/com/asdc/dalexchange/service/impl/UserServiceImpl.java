package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.UserDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.model.VerificationCode;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.repository.VerificationCodeRepository;
import com.asdc.dalexchange.service.EmailService;
import com.asdc.dalexchange.service.UserService;
import com.asdc.dalexchange.util.AuthUtil;
import com.asdc.dalexchange.util.CloudinaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Mapper<User, UserDTO> userMapper;

    @Autowired
    private CloudinaryUtil cloudinaryUtil;

    public long newCustomers(){
        return userRepository.countUsersJoinedInLast30Days();
    }

    public double customersChange() {
        LocalDateTime now = getCurrentDateTime();
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

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }


    private double calculatePercentageIncrease(Double current, Double previous) {
        if (previous == null || previous == 0) {
            return current != null && current > 0 ? 100.0 : 0.0;
        }
        if (current == null) {
            current = 0.0;
        }
        return ((current - previous) / previous) * 100;
    }

    //new

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> viewUserDetails(long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapTo);
    }

    @Override
    public UserDTO editUserDetails(long userId, UserDTO updatedUserDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUserDetails.getEmail() != null) {
            user.setEmail(updatedUserDetails.getEmail());
        }
        if (updatedUserDetails.getPhoneNo() != null) {
            user.setPhoneNo(updatedUserDetails.getPhoneNo());
        }
        if (updatedUserDetails.getFullName() != null) {
            user.setFullName(updatedUserDetails.getFullName());
        }
        if (updatedUserDetails.getRole() != null) {
            user.setRole(updatedUserDetails.getRole());
        }
        if (updatedUserDetails.getActive() != null) {
            user.setActive(updatedUserDetails.getActive());
        }

        return userMapper.mapTo(userRepository.save(user));
    }

    @Override
    public UserDTO activateUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(true);
            return userMapper.mapTo(userRepository.save(user));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDTO deactivateUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(false);
            return userMapper.mapTo(userRepository.save(user));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Delete User Account
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    // Reset Password
//    public User resetPassword(long userId, String newPassword) {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.setPassword(newPassword);
//            return userRepository.save(user);
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }

    public User registerUser(User user, MultipartFile profilePicture) {

        String profilePictureURL = cloudinaryUtil.uploadImage(profilePicture);

        user.setProfilePicture(profilePictureURL);

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

    public User getCurrentUser() {
        return AuthUtil.getCurrentUser(userRepository);
    }

}
