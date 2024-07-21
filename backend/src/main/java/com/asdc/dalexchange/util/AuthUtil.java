package com.asdc.dalexchange.util;

import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class AuthUtil {

    public static User getCurrentUser(UserRepository userRepository) {
        return userRepository.findByEmail(getEmail()).orElse(null);
    }

    public static Long getCurrentUserId(UserRepository userRepository) {
        User currentUser = getCurrentUser(userRepository);
        log.info("the current user is " + currentUser);
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
        log.info("the authentication is " + authentication);
        String email;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        log.info("the email is " + email);
        return email;
    }
}
