package com.lapuka.crm.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.data.domain.Page;

import com.lapuka.crm.model.User;
import com.lapuka.crm.dto.UserRegistrationDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllEmployees();
    User findByUsername(String username);

    User findByEmail(String email);

    void deleteById(long id);

    User save(UserRegistrationDto registration);

    Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}