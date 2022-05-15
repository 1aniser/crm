package com.lapuka.crm.repositoryTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.junit.Assert;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class applicationRepositoryTests {
    @Autowired
    private ApplicationRepository applicationRepository;

    private Application application;
    private User user;

    String sortDirection = "asc";
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("id").ascending() :
            Sort.by("id").descending();
    Pageable pageable = PageRequest.of(0, 10, sort);

    @BeforeEach
    public void setup(){
        user = new User(12L, "mark", "mark@mail.ru", "123", "Романов Марк Тимофеевич", "1119992");
        application = new Application(1L, "тема", "описание", user, LocalDateTime.now(), "Новая");
    }

    @Test
    public void testSave(){
        application = applicationRepository.save(application);
        Assert.assertNotNull(application);
    }

    @Test
    public void testFindBySubjectContaining(){
        Page<Application> applicationPage = applicationRepository.findBySubjectContaining("тема", pageable);
        Assert.assertFalse(applicationPage.isEmpty());
    }

    @Test
    public void testFindBySubjectContainingAndUserApl(){
        Page<Application> applicationPage = applicationRepository.findBySubjectContainingAndUserApl("тема", user, pageable);
        Assert.assertFalse(applicationPage.isEmpty());
    }

    @Test
    public void testFindByUserAplLike(){
        Page<Application> applicationPage = applicationRepository.findByUserAplLike(user, pageable);
        Assert.assertFalse(applicationPage.isEmpty());
    }

    @Test
    public void testFindByUserAplLikeList(){
        ArrayList<Application> applicationPage = applicationRepository.findByUserAplLike(user);
        Assert.assertFalse(applicationPage.isEmpty());
    }

    @Test
    public void testFindByStatusLike(){
        ArrayList<Application> applicationPage = applicationRepository.findByStatusLike("Новая");
        Assert.assertFalse(applicationPage.isEmpty());
    }
    @Test
    public void testFindAll(){
        List<Application> application = applicationRepository.findAll();
        assertTrue("", application.size() > 0);
    }
}
