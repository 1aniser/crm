package com.lapuka.crm.repositoryTests;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.OrderRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;

    private Orders order;
    private User user;

    String sortDirection = "asc";
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("id").ascending() :
            Sort.by("id").descending();
    Pageable pageable = PageRequest.of(0, 10, sort);

    @BeforeEach
    public void setup(){
        user = new User(12L, "mark", "mark@mail.ru", "123", "Романов Марк Тимофеевич", "1119992");
        order = new Orders(1L, "тема", "описание", user, LocalDateTime.now(), LocalDateTime.now(), "Готов", 123);
    }

    @Test
    public void testSave(){
        order = orderRepository.save(order);
        Assert.assertNotNull(order);
    }


    @Test
    public void testFindBySubjectContaining(){
        Page<Orders> ordersPage = orderRepository.findBySubjectContaining("тема", pageable);
        Assert.assertFalse(ordersPage.isEmpty());
    }

    @Test
    public void testFindBySubjectContainingAndUserApl(){
        Page<Orders> ordersPage = orderRepository.findBySubjectContainingAndUser("тема", user, pageable);
        Assert.assertFalse(ordersPage.isEmpty());
    }

    @Test
    public void testFindByUserLike(){
        Page<Orders> ordersPage = orderRepository.findByUserLike(user, pageable);
        Assert.assertFalse(ordersPage.isEmpty());
    }

    @Test
    public void testFindByUserLikeList(){
        ArrayList<Orders> ordersList = orderRepository.findByUserLike(user);
        Assert.assertFalse(ordersList.isEmpty());
    }

    @Test
    public void testFindByStatusLike(){
        ArrayList<Orders> ordersList = orderRepository.findByStatusLike("Готов");
        Assert.assertFalse(ordersList.isEmpty());
    }

    @Test
    public void testFindAll(){
        List<Orders> order = orderRepository.findAll();
        assertTrue("", order.size() > 0);
    }
}
