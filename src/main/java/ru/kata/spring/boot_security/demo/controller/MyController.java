package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class MyController {
    private final Logger logger = Logger.getLogger(MyController.class.getName());
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private void setUserService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/admin/adminstartpage")
    public String showAdminPage(ModelMap model) {
        List<User> users = new ArrayList<>(userDetailsService.allUsers());
        model.addAttribute("userlist", users);
        return "/adminstartpage";
    }

    @GetMapping("/user")
    public String showUserPage(ModelMap model, Principal principal) {
        model.addAttribute("user", userDetailsService.findUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping(value = "/admin/add")
    public String show(ModelMap model) {
        model.addAttribute("user", new User());
        return "adduser";
    }


    @PostMapping(value = "/admin/add")
    public String add(@ModelAttribute("user") User user) {
        userDetailsService.saveUser(user);
        return "redirect:/admin/adminstartpage";
    }

    @GetMapping(value = "/admin/edit/{id}")
    public String editUser(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("editeduser", userDetailsService.findUserById(id));
        model.addAttribute("role", new StringBuilder());
        return "edituser";
    }

    @PostMapping(value = "/admin/edit")
    public String edit(@ModelAttribute("editeduser") User user, @RequestParam("role") String role) {
        logger.info(role.equalsIgnoreCase("ADMIN") ? "admin" : "not_user");
        userDetailsService.setRole(user, role);
        userDetailsService.editUser(user);
        return "redirect:/admin/adminstartpage";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userDetailsService.deleteUser(userDetailsService.findUserById(id));
        return "redirect:/admin/adminstartpage";
    }
}
