package com.lapuka.crm.modelTests;

import com.lapuka.crm.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class roleTests {
    private Role role;

    @BeforeEach
    void setUp(){
        role = new Role("ROLE_USER");
    }

    @Test
    void testRole()throws IllegalArgumentException{
        if(!role.getName().equals("ROLE_USER")) throw new IllegalArgumentException();
    }
}
