package ru.didenko.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.didenko.domain.Post;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCacheServiceImpl implements CacheService<Post> {
    private static final String POSTS_CACHE = "postsCache";
    private static final String IS_VALID_CACHE = "isValidCache";

    private final RedisTemplate<String, Post> redisPostTemplate;
    private final RedisTemplate<String, Boolean> redisBooleanTemplate;

    public void validCache() {
        if (isClosed()) return;
        redisBooleanTemplate.opsForValue().set(IS_VALID_CACHE, true);
    }

    public Boolean isValidCache() {
        if (isClosed()) return false;
        return Optional.ofNullable(redisBooleanTemplate.opsForValue().get(IS_VALID_CACHE)).orElse(false);
    }

    public void addToCache(Post post) {
        if (isClosed()) return;

        redisPostTemplate.opsForList().leftPush(POSTS_CACHE, post);
    }

    public void addToCache(List<Post> posts) {
        if (isClosed()) return;
        redisPostTemplate.opsForList().rightPushAll(POSTS_CACHE, posts);
    }

    public List<Post> getFromCache(Long offset, Long limit) {
        if (isClosed()) return Collections.emptyList();
        return redisPostTemplate.opsForList().range(POSTS_CACHE, offset, offset + limit);
    }

    public void deleteFromCache(Long id) {
        if (isClosed()) return;
        findById(id).ifPresent(this::deletePost);
    }

    public void update(Post newPost) {
        findById(newPost.getId()).ifPresent((post -> {
            Long index = redisPostTemplate.opsForList().indexOf(POSTS_CACHE, post);
            redisPostTemplate.opsForList().set(POSTS_CACHE, index, newPost);
        }));
    }

    private void deletePost(Post post) {
        redisPostTemplate.opsForList().remove(POSTS_CACHE, 0, post);
    }

    public Optional<Post> findById(Long id) {
        if (isClosed()) return Optional.empty();
        return redisPostTemplate.opsForList()
                .range(POSTS_CACHE, 0, -1)
                .stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    @Override
    public Long getSizeCache() {
        if (isClosed()) return 0L;
        return redisPostTemplate.opsForList().size(POSTS_CACHE);
    }

    private boolean isClosed() {
        try {
            return Objects.requireNonNull(redisPostTemplate.getConnectionFactory())
                    .getConnection()
                    .isClosed();
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            return true;
        }
    }
}
