package com.lapuka.crm.repositoryTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.UserRepository;
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
import java.util.Iterator;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    private User user;

    String sortDirection = "asc";
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("id").ascending() :
            Sort.by("id").descending();
    Pageable pageable = PageRequest.of(0, 10, sort);


    @BeforeEach
    public void setup(){
        user = new User(12L, "mark", "mark@mail.ru", "123", "Романов Марк Тимофеевич", "1119992");
    }

    @Test
    public void testSave(){
        user = userRepository.save(user);
        Assert.assertNotNull(user);
    }


    @Test
    public void testFindByUsername(){
        user = userRepository.findByUsername("mark");
        Assert.assertFalse(user.equals(0));
    }

    @Test
    public void testFindByEmail(){
        user = userRepository.findByEmail("mark@mail.ru");
        Assert.assertFalse(user.equals(0));
    }

    @Test
    public void testFindByUsernameContaining(){
        Page<User> userPage = userRepository.findByUsernameContaining("mark", pageable);
        Assert.assertFalse(userPage.isEmpty());
    }

    @Test
    public void testFindAll(){
        List<User> userList = userRepository.findAll();
        assertTrue("", userList.size() > 0);
    }
}
