package com.lapuka.crm.service;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public User findBySubject(String subject) {
        return orderRepository.findBySubject(subject);
    }

    @Override
    public Page<Orders> findPaginated(String keyword, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (keyword != null) {
            return orderRepository.findBySubjectContaining(keyword, pageable);
        }
        return this.orderRepository.findByUserLike(auth.getName(), pageable);
    }
}
