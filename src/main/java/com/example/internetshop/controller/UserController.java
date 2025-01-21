package com.example.internetshop.controller;

import com.example.internetshop.model.User;
import com.example.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "users/view";
                })
                .orElse("error/404");
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "users/edit";
                })
                .orElse("error/404");
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        userService.updateUser(id, updatedUser);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}