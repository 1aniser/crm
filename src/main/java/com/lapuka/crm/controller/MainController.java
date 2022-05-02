package com.lapuka.crm.controller;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @GetMapping("/statistics")
    public String statistics(Model model) {
        return "statistics";
    }

    @GetMapping("/addOrder")
    public String addOrder(Model model) {
        return "addOrder";
    }

    @PostMapping("/addOrder")
    public String addNewOrder(@RequestParam String subject, @RequestParam String description, @CurrentSecurityContext(expression="authentication?.name") String username, Model model){
        Orders order = new Orders(subject, description, username, new Date(), "Новый");
        orderRepository.save(order);
        return "redirect:/";
    }
}