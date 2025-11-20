package ru.didenko.dao;

import ru.didenko.domain.User;

import java.util.List;

public interface UserRepository {

    Integer insert(User user);

    User findById(long id);

    List<User> findAll();

    void deleteById(long id);
}
