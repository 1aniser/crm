package com.lapuka.crm.serviceTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Role;
import com.lapuka.crm.model.User;
import com.lapuka.crm.service.ApplicationServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ApplicationServiceTests {
    @Autowired
    ApplicationServiceImpl applicationService;

    private Application application;
    private User user;

    private Role role;
    String sortDirection = "asc";
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("id").ascending() :
            Sort.by("id").descending();
    Pageable pageable = PageRequest.of(0, 10, sort);

    @BeforeEach
    void setUp()throws Exception{
        user = new User(12L, "mark", "mark@mail.ru", "123", "Романов Марк Тимофеевич", "1119992");
        application = new Application(1L, "тема", "описание", user, LocalDateTime.now(), "Новая");
        role = new Role("ROLE_USER");
    }

    @Test
    public void testGetAllOrders(){
        List<Application> applications = applicationService.getAllApplications();
        Assert.assertTrue(applications.size() != 0);
    }

    @Test
    public void testFindUserApplications(){
        List<Application> applications = applicationService.findUserApplications(user.getId());
        Assert.assertTrue(applications.size() != 0);
    }

    @Test
    public void testFindUserApplications2(){
        List<Application> applications = applicationService.findUserApplications(8L);
        Assert.assertTrue(applications.size() != 0);
    }

    @Test
    public void testFindPaginatedAsc(){
        Page<Application> applicationPage = applicationService.findPaginated(user, null, 1, 10, "id", "asc");
        Assert.assertFalse(applicationPage.isEmpty());
    }

    @Test
    public void testFindPaginatedDesc(){
        Page<Application> applicationPage = applicationService.findPaginated(user, null, 1, 10, "id", "desc");
        Assert.assertFalse(applicationPage.isEmpty());
    }

    @Test
    public void testFindPaginatedAscKeyword(){
        Page<Application> applicationPage = applicationService.findPaginated(user, "тема", 1, 10, "id", "asc");
        Assert.assertFalse(applicationPage.isEmpty());
    }

    @Test
    public void testFindPaginatedDescKeyword(){
        Page<Application> applicationPage = applicationService.findPaginated(user, "тема", 1, 10, "id", "desc");
        Assert.assertFalse(applicationPage.isEmpty());
    }
}
