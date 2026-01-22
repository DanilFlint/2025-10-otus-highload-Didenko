package ru.didenko.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.didenko.domain.User;
import ru.didenko.domain.UserDTO;
import ru.didenko.service.UserService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getById(id).toDto();
    }

    @Validated
    @GetMapping(value = "/users/search", params = {"first_name", "last_name"})
    public List<UserDTO> getUsersByNameAndLastname(
            @RequestParam(name = "first_name") @NotBlank String name,
            @RequestParam(name = "last_name") @NotBlank String lastname
    ) {
        return userService.getByNameAndLastname(name, lastname)
                .stream().map(User::toDto).sorted(Comparator.comparing(UserDTO::getId)).toList();
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers(Model model) {
        return userService.getAll()
                .stream().map(User::toDto).sorted(Comparator.comparing(UserDTO::getId)).toList();
    }
}
