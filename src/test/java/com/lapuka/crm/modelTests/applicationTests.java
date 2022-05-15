package com.lapuka.crm.modelTests;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class applicationTests {
    private Application application;
    private User user;

    @BeforeEach
    void setUp(){
        user = new User(12L, "mark", "mark@mail.ru", "123", "Романов Марк Тимофеевич", "1119992");
        application = new Application(1L, "тема", "описание", user, LocalDateTime.now(), "Новая");
    }

    @Test
    void testApplication()throws IllegalArgumentException{
        if(!application.getId().equals(1L)) throw new IllegalArgumentException();
        if(!application.getSubject().equals("тема")) throw new IllegalArgumentException();
        if(!application.getDescription().equals("описание")) throw new IllegalArgumentException();
        if(!application.getUserApl().equals(user)) throw new IllegalArgumentException();
        if(!application.getStatus().equals("Новая")) throw new IllegalArgumentException();
    }
}
