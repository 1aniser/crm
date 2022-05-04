package com.lapuka.crm.controller;

import com.lapuka.crm.model.Orders;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/")
    public String root(Model model) {
        return findPaginated(null,1, "id", "asc", model);
    }

    @GetMapping("/home")
    public String home(Model model) {
        return findPaginated(null,1, "id", "asc", model);
    }

    @RequestMapping("/search")
    public String search(Model model, String keyword){
        return findPaginated(keyword,1, "id", "asc", model);
    }

    @GetMapping("home/page/{pageNo}")
    public String findPaginated(@RequestParam(value = "keyword", required = false) String keyword,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Page<Orders> page = orderService.findPaginated(keyword, pageNo, pageSize, sortField, sortDir);
        List<Orders> listOrders = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listorders", listOrders);
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

    @GetMapping("/order/{id}")
    public String orderDetails(@PathVariable(value = "id") Long id, Model model) {
        if(!orderRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Orders> order = orderRepository.findById(id);
        ArrayList<Orders> res = new ArrayList<>();
        order.ifPresent(res::add);
        model.addAttribute("order", res);
        return "orderDetails";
    }

    @GetMapping("/order/{id}/edit")
    public String orderEdit(@PathVariable(value = "id") Long id, Model model) {
        if(!orderRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Orders> order = orderRepository.findById(id);
        ArrayList<Orders> res = new ArrayList<>();
        order.ifPresent(res::add);
        model.addAttribute("order", res);
        return "orderEdit";
    }

    @PostMapping("/order/{id}/edit")
    public String orderUpdate(@PathVariable(value = "id") Long id, @RequestParam String subject, @RequestParam String description, @CurrentSecurityContext(expression="authentication?.name") String username, Model model){
        Orders order = orderRepository.findById(id).orElseThrow();
        order.setSubject(subject);
        order.setDescription(description);
        orderRepository.save(order);
        return "redirect:/";
    }

    @PostMapping("/order/{id}/delete")
    public String orderDelete(@PathVariable(value = "id") Long id, Model model){
        Orders order = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(order);
        return "redirect:/";
    }
}