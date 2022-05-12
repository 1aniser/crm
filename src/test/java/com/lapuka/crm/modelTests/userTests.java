package com.lapuka.crm.modelTests;

import com.lapuka.crm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userTests {
    private User user;

    @BeforeEach
    void setUp()throws Exception{
        user = new User(1L, "пользователь", "почта@gmail.com", "пароль", "фио", "1234567");
    }

    @Test
    void testUser()throws IllegalArgumentException{
        if(!user.getId().equals(1L)) throw new IllegalArgumentException();
        if(!user.getUsername().equals("пользователь")) throw new IllegalArgumentException();
        if(!user.getEmail().equals("почта@gmail.com")) throw new IllegalArgumentException();
        if(!user.getPassword().equals("пароль")) throw new IllegalArgumentException();
        if(!user.getFio().equals("фио")) throw new IllegalArgumentException();
        if(!user.getPhone().equals("1234567")) throw new IllegalArgumentException();
    }
}
