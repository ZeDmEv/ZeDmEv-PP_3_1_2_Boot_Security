package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class MyController {
    private final Logger logger = Logger.getLogger(MyController.class.getName());
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private void setUserService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(value = "/")
    public String start() {
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String showAdminPage(ModelMap model, Authentication authentication) {
        User user = userDetailsService.findUserByUsername(authentication.getName());
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        List<User> users = new ArrayList<>(userDetailsService.allUsers());
        List<Role> allRoles = new ArrayList<>(userDetailsService.getAllRoles());
        model.addAttribute("allroles", allRoles);
        model.addAttribute("thisuser", user);
        model.addAttribute("newuser", new User());
        model.addAttribute("userroles", roles);
        model.addAttribute("userlist", users);
        return "mybootstrapstudypage";
    }

    @GetMapping("/user")
    public String showUserPage(ModelMap model, Principal principal) {
        model.addAttribute("thisuser", userDetailsService.findUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping(value = "/admin/add")
    public String show(ModelMap model) {
        model.addAttribute("user", new User());
        return "adduser";
    }


    @PostMapping(value = "/admin/add")
    public String add(@ModelAttribute("newuser") User newUser,
                      @ModelAttribute("newUserRoles") String role) {
        userDetailsService.setRole(newUser, role);
        userDetailsService.saveUser(newUser);
        logger.info("Role set to: " + newUser.getRolesToString());
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/edit")
    public String edit(@ModelAttribute("user") User user, @ModelAttribute("editedUserRoles") String role) {
        logger.info("Current user id is: " + user.getId());
        userDetailsService.setRole(user, role);
        userDetailsService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String delete(@ModelAttribute("user") User user) {
        userDetailsService.deleteUser(userDetailsService.findUserById(user.getId()));
        return "redirect:/admin";
    }
}
