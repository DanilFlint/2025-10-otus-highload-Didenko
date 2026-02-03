package ru.didenko.service;

import ru.didenko.domain.User;

import java.util.List;

public interface UserService {

    Integer insert(User user);

    User getById(Long id);

    List<User> getByNameAndLastname(String name, String lastname);

    List<User> getAll();

}
