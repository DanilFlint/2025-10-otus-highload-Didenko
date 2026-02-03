package ru.didenko.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.didenko.cache.CacheService;
import ru.didenko.repository.PostRepository;
import ru.didenko.domain.Post;
import ru.didenko.service.PostService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CacheService<Post> postCacheService;

    @Override
    public Long insert(Post post) {
        postRepository.insert(post).ifPresent(id -> {
            post.setId(id);
            postCacheService.addToCache(post);
        });
        return post.getId();
    }

    @Override
    public Long update(Post post) {
        postRepository.update(post);
        postCacheService.update(post);
        return post.getId();
    }

    @Override
    public Post getById(long id) {
        return postCacheService.findById(id)
                .orElseGet(() -> postRepository.findById(id));
    }

    @Override
    public List<Post> getPage(Long offset, Long limit) {
        if(postCacheService.isValidCache()) {
            return postCacheService.getFromCache(offset, limit);
        }

        Long sizeCache = postCacheService.getSizeCache();
        List<Post> posts = postRepository.findLastsByDate(offset + sizeCache, limit - sizeCache);
        postCacheService.addToCache(posts);
        postCacheService.validCache();
        return posts;
    }

    @Override
    public void deleteById(long id) {
        postRepository.deleteById(id);
        postCacheService.deleteFromCache(id);
    }
}
