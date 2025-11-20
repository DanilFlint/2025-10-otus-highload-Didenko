package ru.didenko.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.didenko.dao.UserRepository;
import ru.didenko.domain.RegistrationForm;

@RestController
@RequiredArgsConstructor
public class RestRegistrationController {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @PostMapping("/user/register")
    public Integer registerUser(RegistrationForm form) {
        return repository.insert(form.toUser(encoder));
    }
}
