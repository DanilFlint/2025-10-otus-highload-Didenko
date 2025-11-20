package ru.didenko.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.didenko.domain.User;
import ru.didenko.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping("/users")
    public List<User> getUsers(Model model) {
        return userService.getAll();
    }
}
