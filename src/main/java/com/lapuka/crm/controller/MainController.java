package com.lapuka.crm.controller;

import com.lapuka.crm.model.Order;
import com.lapuka.crm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/")
    public String root(Model model) {
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}