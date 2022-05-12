package com.lapuka.crm.serviceTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.ApplicationServiceImpl;
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
public class ApplicationServiceTests {
    @Autowired
    ApplicationServiceImpl applicationService;

    @MockBean
    ApplicationRepository applicationRepositoryMock;

    private Application application;
    private User user;

    @BeforeEach
    void setUp()throws Exception{
        user = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");
        application = new Application(1L, "тема", "описание", user, LocalDateTime.now(), "Новая");
    }

    @Test
    public void getAllApplicationsTest() throws IllegalArgumentException{
        List<Application> applicationsFromMock = new ArrayList<>();
        Mockito.when(applicationRepositoryMock.findAll()).thenReturn(applicationsFromMock);
        List<Application> applications = applicationService.getAllApplications();
        if(!applications.equals(applicationsFromMock)) throw new IllegalArgumentException();
    }
}
