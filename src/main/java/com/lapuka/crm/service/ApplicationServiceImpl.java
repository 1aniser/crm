package com.lapuka.crm.service;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Page<Application> findPaginated(User user, String keyword, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
                if (keyword != null) {
                    return applicationRepository.findBySubjectContaining(keyword, pageable);
                }
                return this.applicationRepository.findAll(pageable);
            }
            else {
                if (keyword != null) {
                    return applicationRepository.findBySubjectContainingAndUserApl(keyword, user, pageable);
                }
                return this.applicationRepository.findByUserAplLike(user, pageable);
            }
        }
        else {
            if (keyword != null) {
                return applicationRepository.findBySubjectContaining(keyword, pageable);
            }
            return this.applicationRepository.findAll(pageable);
        }
    }

    @Override
    public ArrayList<Application> findUserApplications(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        return this.applicationRepository.findByUserAplLike(user);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findByUsername(name);
    }
}
