package ru.didenko.service;

import ru.didenko.domain.Post;

import java.util.List;

public interface PostService {
    Long insert(Post post);

    Long update(Post post);

    Post getById(long id);

    List<Post> getPage(Long offset, Long limit);

    void deleteById(long id);
}
