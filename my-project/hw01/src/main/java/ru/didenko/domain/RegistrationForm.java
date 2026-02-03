package ru.didenko.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {

    private String name;

    private String lastname;

    private String dateOfBirth;

    private String city;

    private String gender;

    private String interests;

    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(name)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .city(city).gender(gender)
                .interests(interests)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
