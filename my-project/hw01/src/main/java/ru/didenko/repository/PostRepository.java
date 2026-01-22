package ru.didenko.repository;

import ru.didenko.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Integer> insert(Post post);

    Integer update(Post post);

    Post findById(long id);

    List<Post> findLastsByDate(Long offset, Long limit);

    void deleteById(long id);
}
