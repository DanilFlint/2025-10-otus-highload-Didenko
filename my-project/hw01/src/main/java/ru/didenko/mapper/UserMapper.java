package ru.didenko.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.didenko.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .lastname(rs.getString("lastname"))
                .dateOfBirth(rs.getString("date_of_birth"))
                .city(rs.getString("city"))
                .gender(rs.getString("gender"))
                .interests(rs.getString("interests"))
                .password(rs.getString("password"))
                .build();
    }
}
