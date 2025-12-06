package ru.didenko.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private long id;

    private String username;

    private String lastname;

    private String dateOfBirth;

    private String city;
}
