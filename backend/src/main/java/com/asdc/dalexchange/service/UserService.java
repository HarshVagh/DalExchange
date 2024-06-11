package com.asdc.dalexchange.service;

import com.asdc.dalexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
