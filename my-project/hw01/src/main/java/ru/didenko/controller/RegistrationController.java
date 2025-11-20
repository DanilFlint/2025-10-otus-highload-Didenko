package ru.didenko.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.didenko.dao.UserRepository;
import ru.didenko.domain.RegistrationForm;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mvc")
public class RegistrationController {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @GetMapping("/user/register")
    public String registerPage(Model model) {
        RegistrationForm form = new RegistrationForm();
        model.addAttribute("form", form);
        return "register";
    }

    @PostMapping("/user/register")
    public String registerUser(RegistrationForm form) {
        repository.insert(form.toUser(encoder));
        return "redirect:/";
    }
}
