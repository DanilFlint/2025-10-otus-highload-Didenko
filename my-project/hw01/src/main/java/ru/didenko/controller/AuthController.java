package ru.didenko.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.didenko.domain.User;
import ru.didenko.repository.UserRepository;
import ru.didenko.security.JwtTokenProvider;

@RestController
class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager am, UserRepository ur, PasswordEncoder pe, JwtTokenProvider tp) {
        this.authenticationManager = am;
        this.userRepository = ur;
        this.passwordEncoder = pe;
        this.tokenProvider = tp;
    }

    @PostMapping("/api/user/register")
    public Integer register(@RequestBody RegisterForm registerForm) {
        return userRepository.insert(registerForm.toUser(passwordEncoder));
    }

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Попытка входа. ID: " + loginRequest.id());

        if (loginRequest.password() == null) {
            System.out.println("Ошибка: Пароль пришел как null. Проверьте кавычки в JSON.");
            return ResponseEntity.badRequest().body("Пароль не может быть пустым");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.id(),
                            loginRequest.password()
                    )
            );

            System.out.println("Аутентификация успешна для пользователя с ID: " + loginRequest.id());
            String token = tokenProvider.generateToken(auth);
            return ResponseEntity.ok(token);

        } catch (AuthenticationException e) {
            System.out.println("Сбой аутентификации для пользователя с ID " + loginRequest.id() + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).body("Неверный логин или пароль");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Внутренняя ошибка сервера: " + e.getMessage());
        }
    }

    public record RegisterForm(
            String username,
            String lastname,
            String password,
            String city,
            String gender,
            String dateOfBirth,
            String interests) {
        public User toUser(PasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .lastname(lastname)
                    .dateOfBirth(dateOfBirth)
                    .city(city)
                    .gender(gender)
                    .interests(interests)
                    .password(passwordEncoder.encode(password))
                    .build();
        }
    }

    public record LoginRequest(String id, String password) {}
}
