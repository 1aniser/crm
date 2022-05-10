package com.lapuka.crm.repository;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findBySubjectContaining(String subject, Pageable pageable);
    Page<Orders> findBySubjectContainingAndUser(String subject, User user, Pageable pageable);
    Page<Orders> findByUserLike(User user, Pageable pageable);
    ArrayList<Orders> findByUserLike(User user);

    ArrayList<Orders> findByStatusLike(String status);

}
