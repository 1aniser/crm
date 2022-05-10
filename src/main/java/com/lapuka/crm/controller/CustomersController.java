package com.lapuka.crm.controller;

import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.ApplicationServiceImpl;
import com.lapuka.crm.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomersController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ApplicationServiceImpl orderService;

    @GetMapping("/customers")
    public String customers(Model model) {
        return findPaginated(null,1, "id", "asc", model);
    }

    @RequestMapping("/customers/search")
    public String search(Model model, String keyword){
        return findPaginated(keyword,1, "id", "asc", model);
    }

    @GetMapping("customers/page/{pageNo}")
    public String findPaginated(@RequestParam(value = "keyword", required = false) String keyword,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;
        Page<User> page = userService.findPaginated(keyword, pageNo, pageSize, sortField, sortDir);
        List<User> listUsers = page.getContent();
        model.addAttribute("keyword", keyword);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listusers", listUsers);

        return "customers";
    }
}
