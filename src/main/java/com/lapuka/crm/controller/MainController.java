package com.lapuka.crm.controller;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String root(Model model) {
        Iterable<Orders> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Iterable<Orders> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "customers";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        return "statistics";
    }

    @GetMapping("/addOrder")
    public String addOrder(Model model) {
        return "addOrder";
    }
}