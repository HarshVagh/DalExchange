package com.asdc.dalexchange.service;


import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CustomUserDetailsService {

     UserDetails loadUserByUsername(String email);
}
