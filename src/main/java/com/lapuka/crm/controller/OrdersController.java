package com.lapuka.crm.controller;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.SingletonStatus;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.ApplicationServiceImpl;
import com.lapuka.crm.service.OrderServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class OrdersController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/orders")
    public String home(Model model) {
        return findPaginatedOrders(null,1, "id", "desc", model);
    }

    @RequestMapping("/searchOrders")
    public String searchOrders(Model model, String keyword){
        return findPaginatedOrders(keyword, 1, "id", "desc", model);
    }

    @GetMapping("orders/page/{pageNo}")
    public String findPaginatedOrders(@RequestParam(value = "keyword", required = false) String keyword,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Page<Orders> page = orderService.findPaginated(user, keyword, pageNo, pageSize, sortField, sortDir);
        List<Orders> listOrders = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listOrders", listOrders);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String applicationDetails(@PathVariable(value = "id") Long id, Model model) {
        if(!orderRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Orders> applicationOptional = orderRepository.findById(id);
        ArrayList<Orders> res = new ArrayList<>();
        applicationOptional.ifPresent(res::add);
        model.addAttribute("ordersAtr", res);
        return "orderDetails";
    }

    @GetMapping("/orders/{id}/edit")
    public String applicationEdit(@PathVariable(value = "id") Long id, Model model) {
        if(!orderRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Orders> order = orderRepository.findById(id);
        ArrayList<Orders> res = new ArrayList<>();
        order.ifPresent(res::add);
        model.addAttribute("ordersAtr", res);
        return "orderEdit";
    }

    @PostMapping("/orders/{id}/edit")
    public String orderUpdate(@PathVariable(value = "id") Long id, @RequestParam String subject, @RequestParam String description, @CurrentSecurityContext(expression="authentication?.name") String username, Model model){
        Orders order = orderRepository.findById(id).orElseThrow();
        order.setSubject(subject);
        order.setDescription(description);
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @PostMapping("/orders/{id}/delete")
    public String orderDelete(@PathVariable(value = "id") Long id, Model model){
        Orders order = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(order);
        return "redirect:/orders";
    }

    @PostMapping("/orders/{id}/complete")
    public String applicationExecute(@PathVariable(value = "id") Long id, Model model){
        Orders order = orderRepository.findById(id).orElseThrow();
        order.setStatus(SingletonStatus.COMPLETED.getStatus());
        String fileName = "Заказ " + order.getId() + ".txt";
        try(FileWriter writer = new FileWriter(fileName, false))
        {
            String text = "     Заказ №" + order.getId() + " выполнен!";
            writer.write(text);
            writer.append('\n');
            text = "------------------------------";
            writer.write(text);
            writer.append('\n');
            text = "Тема заказа: "+order.getSubject();
            writer.write(text);
            writer.append('\n');
            text = "Заказчик: "+order.getUser().getFio();
            writer.write(text);
            writer.append('\n');
            text = "Дата создания: "+ order.getDatecreated();
            writer.write(text);
            writer.append('\n');
            text = "Дата начала: "+order.getDateconfirmed();
            writer.write(text);
            writer.append('\n');
            text = "------------------------------";
            writer.write(text);
            writer.append('\n');
            text = "К оплате: "+order.getPrice() + " руб.";
            writer.write(text);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        orderRepository.save(order);
        return "redirect:/";
    }
}
