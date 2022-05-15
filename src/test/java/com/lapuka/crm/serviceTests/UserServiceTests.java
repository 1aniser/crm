package com.lapuka.crm.serviceTests;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.OrderServiceImpl;
import com.lapuka.crm.service.UserService;
import com.lapuka.crm.service.UserServiceImpl;
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
public class UserServiceTests {
    @Autowired
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp()throws Exception{
        user = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");
    }

    @Test
    public void getAllUsersTest() throws IllegalArgumentException{
        List<User> users = userService.getAllUsers();
        if(!users.equals(users)) throw new IllegalArgumentException();
    }
}
