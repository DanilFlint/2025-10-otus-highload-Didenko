package ru.didenko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.didenko.dao.UserRepository;
import ru.didenko.domain.User;

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
    public User getById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
