package com.lapuka.crm.controller;

import com.lapuka.crm.model.Application;
import com.lapuka.crm.model.Orders;
import com.lapuka.crm.model.SingletonStatus;
import com.lapuka.crm.model.User;
import com.lapuka.crm.repository.ApplicationRepository;
import com.lapuka.crm.repository.OrderRepository;
import com.lapuka.crm.repository.UserRepository;
import com.lapuka.crm.service.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ApplicationServiceImpl applicationService;

    @GetMapping("/")
    public String root(Model model) {
        return findPaginated(null,1, "id", "desc", model);
    }

    @GetMapping("/home")
    public String home(Model model) {
        return findPaginated(null,1, "id", "desc", model);
    }

    @RequestMapping("/search")
    public String search(Model model, String keyword){
        return findPaginated(keyword,1, "id", "desc", model);
    }

    @GetMapping("home/page/{pageNo}")
    public String findPaginated(@RequestParam(value = "keyword", required = false) String keyword,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Page<Application> page = applicationService.findPaginated(user, keyword, pageNo, pageSize, sortField, sortDir);
        List<Application> listApplications = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listApplications", listApplications);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        long allApplications = applicationRepository.count();
        long newAppl = applicationRepository.findByStatusLike(SingletonStatus.NEW.getStatus()).stream().count();
        long rejectedAppl = applicationRepository.findByStatusLike(SingletonStatus.COMPLETING.getStatus()).stream().count();
        long allOrders = orderRepository.count();
        long inprogressOrders = orderRepository.findByStatusLike(SingletonStatus.COMPLETING.getStatus()).stream().count();
        long completedOrders = orderRepository.findByStatusLike(SingletonStatus.COMPLETED.getStatus()).stream().count();
        int newApplPercent = (int) (((float) newAppl/(float)allApplications)*100);
        int complOrdersPercent = (int) (((float) completedOrders/(float)allOrders)*100);
        model.addAttribute("allAppl", allApplications);
        model.addAttribute("newAppl", newAppl);
        model.addAttribute("rejectedAppl", rejectedAppl);
        model.addAttribute("newApplPercent", newApplPercent);
        model.addAttribute("allOrders", allOrders);
        model.addAttribute("inprogressOrders", inprogressOrders);
        model.addAttribute("completedOrders", completedOrders);
        model.addAttribute("complOrdersPercent", complOrdersPercent);
        return "statistics";
    }

    @PostMapping("/statistics/report")
    public String applicationExecute(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        long allApplications = applicationRepository.count();
        long newAppl = applicationRepository.findByStatusLike(SingletonStatus.NEW.getStatus()).stream().count();
        long rejectedAppl = applicationRepository.findByStatusLike(SingletonStatus.COMPLETING.getStatus()).stream().count();
        long allOrders = orderRepository.count();
        long inprogressOrders = orderRepository.findByStatusLike(SingletonStatus.COMPLETING.getStatus()).stream().count();
        long completedOrders = orderRepository.findByStatusLike(SingletonStatus.COMPLETED.getStatus()).stream().count();
        int newApplPercent = (int) (((float) newAppl/(float)allApplications)*100);
        int complOrdersPercent = (int) (((float) completedOrders/(float)allOrders)*100);
        String fileName = "Отчет на момент " + LocalDateTime.now().getDayOfMonth() + '.' + LocalDateTime.now().getMonthValue() + '.' + LocalDateTime.now().getYear() + ".txt";
        try(FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8))
        {
            String text = "  Отчет на момент " + LocalDateTime.now().getDayOfMonth() + '.' + LocalDateTime.now().getMonthValue() + '.' + LocalDateTime.now().getYear() + '\n';
            writer.write(text);
            text = "------------------------------\n";
            writer.write(text);
            text = "           Заявки:\n";
            writer.write(text);
            text = "Всего заявок: "+ allApplications + '\n';
            writer.write(text);
            text = "Новых заявок: "+ newAppl + '\n';
            writer.write(text);
            text = "Отказанных заявок: "+ rejectedAppl + '\n';
            writer.write(text);
            text = "Процент новых: "+ newApplPercent + "%\n";
            writer.write(text);
            text = "------------------------------\n";
            writer.write(text);
            text = "           Заказы:\n";
            writer.write(text);
            text = "Всего заказов: "+ allOrders + '\n';
            writer.write(text);
            text = "Выполняемых заказов: "+ inprogressOrders + '\n';
            writer.write(text);
            text = "Выполненных заказов: "+ completedOrders + '\n';
            writer.write(text);
            text = "Процент выполненных: "+ complOrdersPercent + "%\n";
            writer.write(text);
            text = "------------------------------\n";
            writer.write(text);
            File file = new File(fileName);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return "redirect:/statistics";
    }

    @GetMapping("/addApplication")
    public String addApplication(Model model) {
        return "addApplication";
    }

    @PostMapping("/addApplication")
    public String addNewApplication(@RequestParam String subject, @RequestParam String description, @CurrentSecurityContext(expression="authentication?.name") String name, Model model){
        Application application = new Application(subject, description, applicationService.findUserByName(name), LocalDateTime.now(), SingletonStatus.NEW.getStatus());
        applicationRepository.save(application);
        return "redirect:/";
    }

    @GetMapping("/application/{id}")
    public String applicationDetails(@PathVariable(value = "id") Long id, Model model) {
        if(!applicationRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Application> applicationOptional = applicationRepository.findById(id);
        ArrayList<Application> res = new ArrayList<>();
        applicationOptional.ifPresent(res::add);
        model.addAttribute("applicationAtr", res);
        return "applicationDetails";
    }

    @GetMapping("/application/{id}/edit")
    public String applicationEdit(@PathVariable(value = "id") Long id, Model model) {
        if(!applicationRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Application> application = applicationRepository.findById(id);
        ArrayList<Application> res = new ArrayList<>();
        application.ifPresent(res::add);
        model.addAttribute("applicationAtr", res);
        return "applicationEdit";
    }

    @PostMapping("/application/{id}/edit")
    public String applicationUpdate(@PathVariable(value = "id") Long id, @RequestParam String subject, @RequestParam String description, @CurrentSecurityContext(expression="authentication?.name") String username, Model model){
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setSubject(subject);
        application.setDescription(description);
        applicationRepository.save(application);
        return "redirect:/";
    }

    @PostMapping("/application/{id}/delete")
    public String applicationDelete(@PathVariable(value = "id") Long id, Model model){
        Application application = applicationRepository.findById(id).orElseThrow();
        applicationRepository.delete(application);
        return "redirect:/";
    }

    @PostMapping("/application/{id}/execute")
    public String applicationExecute(@PathVariable(value = "id") Long id, @RequestParam int price, Model model){
        Application application = applicationRepository.findById(id).orElseThrow();
        Orders newOrder = new Orders(application.getSubject(), application.getDescription(), application.getUserApl(), application.getDatecreated(), LocalDateTime.now(), SingletonStatus.COMPLETING.getStatus(), price);
        orderRepository.save(newOrder);
        applicationRepository.delete(application);
        return "redirect:/";
    }

    @PostMapping("/application/{id}/reject")
    public String applicationReject(@PathVariable(value = "id") Long id, Model model){
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setStatus(SingletonStatus.REJECTED.getStatus());
        applicationRepository.save(application);
        return "redirect:/";
    }
}