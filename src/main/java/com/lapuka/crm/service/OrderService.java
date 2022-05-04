package com.lapuka.crm.service;

import com.lapuka.crm.dto.UserRegistrationDto;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    List<Orders> getAllOrders();

    User findBySubject(String subject);

    Page<Orders> findPaginated(String keyword, int pageNo, int pageSize, String sortField, String sortDirection);
}
