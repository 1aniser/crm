package com.lapuka.crm.controller;

import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.ApplicationServiceImpl;
import com.lapuka.crm.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ApplicationServiceImpl applicationService;

    @GetMapping("/profile/{id}")
    public String userDetails(@PathVariable(value = "id") Long id, Model model) {
        if(!userRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<User> userOptional = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        userOptional.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("listApplications", applicationService.findUserApplications(id));
        return "profile";
    }

    @GetMapping("/profile/username/{username}")
    public String usernameToID(@PathVariable(value = "username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        String id = String.valueOf(user.getId());
        return "redirect:/profile/" + id;
    }

    @GetMapping("/profile/{id}/edit")
    public String userEdit(@PathVariable(value = "id") Long id, Model model) {
        if(!userRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "profileEdit";
    }

    @PostMapping("/profile/{id}/edit")
    public String userUpdate(@PathVariable(value = "id") Long id, @RequestParam String fio, @RequestParam String email, @RequestParam String phone, @CurrentSecurityContext(expression="authentication?.name") String username, Model model){
        User user = userRepository.findById(id).orElseThrow();
        user.setFio(fio);
        user.setEmail(email);
        user.setPhone(phone);
        userRepository.save(user);
        return "redirect:/profile/{id}";
    }
}
