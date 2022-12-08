package com.lapuka.crm.controller;

import com.lapuka.crm.model.Chat;
import com.lapuka.crm.model.Chatmessage;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ChatRepository;
import com.lapuka.crm.repository.ChatmessageRepository;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class chatPageController {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatmessageRepository chatmessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/chatDetails/username/{username}")
    public String usernameToID(@PathVariable(value = "username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        String id = String.valueOf(user.getId());
        return "redirect:/chatDetails/usernameid/" + id;
    }

    @GetMapping("/chatDetails/usernameid/{id}")
    public String chatPageDetails(@PathVariable(value = "id") Long id, Model model){
        List<Chatmessage> chatmessages = chatmessageRepository.findByChatLike(chatRepository.findByIdLike(id));
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        model.addAttribute("usernamechat", user.getUsername());
        model.addAttribute("chatmessages", chatmessages);
        return "chatDetails";
    }

    @PostMapping("/addMessage")
    public String addNewMessage(@RequestParam String pageURI, @RequestParam String message, @CurrentSecurityContext(expression="authentication?.name") String name, Model model){
        Long userid = Long.valueOf(pageURI.replace("/chatDetails/usernameid/", ""));
        Chatmessage chat = new Chatmessage(chatRepository.getById(userid), userService.findByUsername(name), message);
        chatmessageRepository.save(chat);
        return "redirect:/chatDetails/usernameid/" + userid;
    }
}
