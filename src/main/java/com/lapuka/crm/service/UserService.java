package com.lapuka.crm.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lapuka.crm.model.User;
import com.lapuka.crm.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}