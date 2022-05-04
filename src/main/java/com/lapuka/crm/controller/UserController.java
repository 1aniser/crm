package com.lapuka.crm.controller;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.OrderServiceImpl;
import com.lapuka.crm.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

}
