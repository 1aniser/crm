package com.lapuka.crm.repository;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findBySubjectContaining(String subject, Pageable pageable);
    Page<Application> findBySubjectContainingAndUserApl(String subject, User user, Pageable pageable);
    Page<Application> findByUserAplLike(User user, Pageable pageable);
    ArrayList<Application> findByUserAplLike(User user);
    ArrayList<Application> findByStatusLike(String status);

}
