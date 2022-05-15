package com.lapuka.crm.serviceTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.service.OrderServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class OrderServiceTests {
    @Autowired
    OrderServiceImpl orderService;

    private Orders order;
    private User user;

    String sortDirection = "asc";
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("id").ascending() :
            Sort.by("id").descending();
    Pageable pageable = PageRequest.of(0, 10, sort);

    @BeforeEach
    void setUp(){
        user = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");
        order = new Orders(1L, "тема", "описание", user, LocalDateTime.now(), LocalDateTime.now(), "Новая", 123);

    }

    @Test
    public void testGetAllOrders(){
        List<Orders> orders = orderService.getAllOrders();
        Assert.assertTrue(orders.size() != 0);
    }

    @Test
    public void testFindUserOrders(){
        List<Orders> orders = orderService.findUserOrders(user.getId());
        Assert.assertTrue(orders.size() != 0);
    }

    @Test
    public void testFindUserOrders2(){
        List<Orders> orders = orderService.findUserOrders(8L);
        Assert.assertTrue(orders.size() != 0);
    }

    @Test
    public void testFindPaginatedAsc(){
        Page<Orders> orderPage = orderService.findPaginated(user, null, 1, 10, "id", "asc");
        Assert.assertFalse(orderPage.isEmpty());
    }

    @Test
    public void testFindPaginatedDesc(){
        Page<Orders> orderPage = orderService.findPaginated(user, null, 1, 10, "id", "desc");
        Assert.assertFalse(orderPage.isEmpty());
    }

    @Test
    public void testFindPaginatedAscKeyword(){
        Page<Orders> orderPage = orderService.findPaginated(user, "тема", 1, 10, "id", "asc");
        Assert.assertFalse(orderPage.isEmpty());
    }

    @Test
    public void testFindPaginatedDescKeyword(){
        Page<Orders> orderPage = orderService.findPaginated(user, "тема", 1, 10, "id", "desc");
        Assert.assertFalse(orderPage.isEmpty());
    }
}
