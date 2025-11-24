package ru.didenko.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.didenko.domain.User;
import ru.didenko.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) {
        return "main";
    }

    @GetMapping("/mvc/users/{id}")
    public String getUser(@PathVariable int id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/mvc/users")
    public String getUsers(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }
}
