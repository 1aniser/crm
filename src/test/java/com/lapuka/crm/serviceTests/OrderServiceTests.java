package com.lapuka.crm.serviceTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderServiceTests {
    @Autowired
    OrderServiceImpl orderService;

    @MockBean
    OrderRepository orderRepositoryMock;

    private Orders order;
    private User user;

    @BeforeEach
    void setUp()throws Exception{
        user = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");
        order = new Orders(1L, "тема", "описание", user, LocalDateTime.now(), LocalDateTime.now(), "Новая", 123);
    }

    @Test
    public void getAllOrdersTest() throws IllegalArgumentException{
        List<Orders> ordersFromMock = new ArrayList<>();
        Mockito.when(orderRepositoryMock.findAll()).thenReturn(ordersFromMock);
        List<Orders> orders = orderService.getAllOrders();
        if(!orders.equals(ordersFromMock)) throw new IllegalArgumentException();
    }
}
