package ru.didenko.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.didenko.repository.PostRepository;
import ru.didenko.domain.Post;
import ru.didenko.mapper.PostMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryJdbc implements PostRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Integer> insert(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("authorUserId", post.getAuthorUserId())
                .addValue("text", post.getText());

        namedParameterJdbcOperations.update(
                "insert into posts (author_user_id, text) values "
                        + "(:authorUserId, :text)",
                parameters,
                keyHolder,
                new String[]{"id"}
        );

        Number key = keyHolder.getKey();
        return Objects.nonNull(key) ? Optional.of(key.intValue()) : Optional.empty();
    }

    @Override
    public Integer update(Post post) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", post.getId())
                .addValue("authorUserId", post.getAuthorUserId())
                .addValue("text", post.getText());

        String sql = "UPDATE posts SET author_user_id = :authorUserId, text = :text WHERE id = :id";

        return namedParameterJdbcOperations.update(sql, parameters);
    }

    @Override
    public Post findById(long id) {
        String sql = "SELECT id, author_user_id, text FROM posts WHERE id = :id";
        return namedParameterJdbcOperations.query(sql,
                Collections.singletonMap("id", id),
                new PostMapper()
        ).stream().findFirst().orElse(null);
    }

    @Override
    public List<Post> findLastsByDate(Long offset, Long limit) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("offset", offset)
                .addValue("limit", limit);

        String sql = "select id, author_user_id, text from posts order by create_date desc offset :offset limit :limit";

        return namedParameterJdbcOperations.query(sql, parameters, new PostMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from posts where id = :id", params
        );
    }
}
