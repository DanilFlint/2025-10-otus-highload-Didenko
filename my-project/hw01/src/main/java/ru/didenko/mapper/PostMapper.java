package ru.didenko.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.didenko.domain.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Post.builder()
                .id(rs.getLong("id"))
                .authorUserId(rs.getLong("author_user_id"))
                .text(rs.getString("text"))
                .build();
    }
}
