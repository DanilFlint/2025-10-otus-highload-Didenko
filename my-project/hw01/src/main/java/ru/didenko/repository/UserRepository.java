package ru.didenko.repository;

import ru.didenko.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Integer insert(User user);

    Optional<User> findById(Long id);

    List<User> findByNameAndLastname(String username, String lastname);

    List<User> findAll();

    void deleteById(long id);
}
