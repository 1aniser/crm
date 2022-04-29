package com.lapuka.crm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lapuka.crm.model.User;
import com.lapuka.crm.service.UserService;
import com.lapuka.crm.dto.UserRegistrationDto;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result) {

        User existing = userService.findByUsername(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "Пользователь с таким именем уже существует");
        }

        User emailExisting = userService.findByEmail(userDto.getEmail());
        if (emailExisting != null) {
            result.rejectValue("email", null, "Пользователь с такой почтой уже существует");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.save(userDto);
        return "redirect:/login";
    }
}