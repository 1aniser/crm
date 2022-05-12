package com.lapuka.crm.repositoryTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
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
public class applicationRepositoryTests {
    @Autowired
    private ApplicationRepository applicationRepository;

    private List<Application> applicationList;

    private Application application;
    private User user;

    @BeforeEach
    public void setup(){
        applicationList = new ArrayList<Application>();

        user = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");
        Application application1 = new Application(1L, "тема", "описание", user, LocalDateTime.now(), "Новая");

        applicationList.add(application1);
    }

    @Test
    public void testSave(){
        for (Iterator iterator = applicationList.iterator(); iterator.hasNext();) {
            Application application = (Application) iterator.next();

            applicationRepository.save(application);

            assertTrue("", application.getId() > 0);
        }
    }

    @Test
    public void testFindAll(){
        List<Application> application = applicationRepository.findAll();

        assertTrue("", application.size() > 0);
    }

}
