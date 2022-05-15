package com.lapuka.crm.service;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

public interface ApplicationService {
    List<Application> getAllApplications();

    Page<Application> findPaginated(User user, String keyword, int pageNo, int pageSize, String sortField, String sortDirection);

    ArrayList<Application> findUserApplications(Long id);

    User findUserByName(String name);
}
