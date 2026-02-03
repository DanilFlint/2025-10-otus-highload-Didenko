package ru.didenko.cache;

import java.util.List;
import java.util.Optional;

public interface CacheService<T> {

    void validCache();

    Boolean isValidCache();

    void addToCache(T record);

    void addToCache(List<T> records);

    List<T> getFromCache(Long offset, Long limit);

    void deleteFromCache(Long id);

    void update(T record);

    Optional<T> findById(Long id);

    Long getSizeCache();
}
