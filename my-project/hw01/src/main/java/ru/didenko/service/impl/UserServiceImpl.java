package ru.didenko.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.didenko.domain.User;
import ru.didenko.repository.UserRepository;
import ru.didenko.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Integer insert(User user) {
        return userRepository.insert(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> getByNameAndLastname(String name, String lastname) {
        return userRepository.findByNameAndLastname(name, lastname);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
