package com.lapuka.crm.repository;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {


    User findBySubject(String username);
    Page<Orders> findBySubjectContaining(String subject, Pageable pageable);
    Page<Orders> findByUserLike(String user, Pageable pageable);
}
