package ru.didenko.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.didenko.domain.User;
import ru.didenko.mapper.UserMapper;
import ru.didenko.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbc implements UserRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Integer insert(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("lastname", user.getLastname())
                .addValue("dateOfBirth", user.getDateOfBirth())
                .addValue("city", user.getCity())
                .addValue("gender", user.getGender())
                .addValue("interests", user.getInterests())
                .addValue("password", user.getPassword());

        namedParameterJdbcOperations.update(
                "insert into users (username, lastname, date_of_birth, city, gender, interests, password) values "
                        + "(:username, :lastname, :dateOfBirth, :city, :gender, :interests, :password)",
                parameters,
                keyHolder,
                new String[]{"id"}
        );

        if (Objects.nonNull(keyHolder.getKey())) {
            return keyHolder.getKey().intValue();
        } else {
            return 0;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                "select id, username, lastname, date_of_birth, city, gender, interests, password from users where id = :id",
                Collections.singletonMap("id", id),
                new UserMapper()
        ));
    }

    @Override
    public List<User> findByNameAndLastname(String username, String lastname) {
        String namePattern = username + "%";
        String lastnamePattern = lastname + "%";

        return namedParameterJdbcOperations.query(
                "SELECT " +
                        "id, username, lastname, date_of_birth, city, gender, interests, password " +
                        "FROM users " +
                        "WHERE lastname LIKE :lastnamePattern AND username LIKE :namePattern",
                Map.of(
                        "lastnamePattern", lastnamePattern,
                        "namePattern", namePattern
                ),
                new UserMapper()
        );
    }

    @Override
    public List<User> findAll() {
        return namedParameterJdbcOperations.query(
                "select id, username, lastname, date_of_birth, city, gender, interests, password from users",
                new UserMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from users where id = :id", params
        );
    }
}
