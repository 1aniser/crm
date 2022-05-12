package com.lapuka.crm.repositoryTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    private List<User> userList;

    private User user;

    @BeforeEach
    public void setup(){
        userList = new ArrayList<User>();

        User user1 = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");

        userList.add(user1);
    }

    @Test
    public void testSave(){
        for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();

            userRepository.save(user);

            assertTrue("", user.getId() > 0);
        }
    }

    @Test
    public void testFindAll(){
        List<User> user = userRepository.findAll();

        assertTrue("", user.size() > 0);
    }
}
