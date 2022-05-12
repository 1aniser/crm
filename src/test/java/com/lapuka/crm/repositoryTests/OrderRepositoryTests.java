package com.lapuka.crm.repositoryTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;

    private List<Orders> ordersList;

    private Orders order;
    private User user;

    @BeforeEach
    public void setup(){
        ordersList = new ArrayList<Orders>();

        user = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");
        Orders order1 = new Orders(1L, "тема", "описание", user, LocalDateTime.now(), LocalDateTime.now(), "Новая", 123);

        ordersList.add(order1);
    }

    @Test
    public void testSave(){
        for (Iterator iterator = ordersList.iterator(); iterator.hasNext();) {
            Orders order = (Orders) iterator.next();

            orderRepository.save(order);

            assertTrue("", order.getId() > 0);
        }
    }

    @Test
    public void testFindAll(){
        List<Orders> order = orderRepository.findAll();

        assertTrue("", order.size() > 0);
    }
}
