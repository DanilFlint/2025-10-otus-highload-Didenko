package ru.didenko.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.didenko.domain.User;
import ru.didenko.mapper.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserRepositoryJdbc implements UserRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public UserRepositoryJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Integer insert(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", user.getUsername())
                .addValue("lastname", user.getLastname())
                .addValue("dateOfBirth", user.getDateOfBirth())
                .addValue("city", user.getCity())
                .addValue("gender", user.getGender())
                .addValue("interests", user.getInterests())
                .addValue("password", user.getPassword());



        namedParameterJdbcOperations.update(
                "insert into users (name, lastname, dateOfBirth, city, gender, interests, password) values "
                        + "(:name, :lastname, :dateOfBirth, :city, :gender, :interests, :password)",
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
    public User findById(long id) {
        return namedParameterJdbcOperations.queryForObject("select id, name, lastname, dateOfBirth, city, gender, interests, password from users where id = :id",
                Collections.singletonMap("id", id),
                new UserMapper()
        );
    }

    @Override
    public List<User> findByNameAndLastname(String username, String lastname) {
        String namePattern = username + "%";
        String lastnamePattern = lastname + "%";

        return namedParameterJdbcOperations.query("SELECT " +
                        "id, name, lastname, dateOfBirth, city, gender, interests, password " +
                        "FROM users " +
                        "WHERE lastname LIKE :lastnamePattern AND name LIKE :namePattern",
                Map.of(
                        "lastnamePattern", lastnamePattern,
                        "namePattern", namePattern
                ),
                new UserMapper()
        );
    }

    @Override
    public List<User> findAll() {
        return namedParameterJdbcOperations.query("select id, name, lastname, dateOfBirth, city, gender, interests, password from users", new UserMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from users where id = :id", params
        );
    }
}
