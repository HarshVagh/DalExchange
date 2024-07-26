package com.asdc.dalexchange.util;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtil {

    public static User getCurrentUser(UserRepository userRepository) {
        return userRepository.findByEmail(getEmail()).orElse(null);
    }

    public static Long getCurrentUserId(UserRepository userRepository) {
        User currentUser = getCurrentUser(userRepository);
        if(currentUser == null) {
            return null;
        }
        return currentUser.getUserId();
    }

    private static String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String email;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }
}
