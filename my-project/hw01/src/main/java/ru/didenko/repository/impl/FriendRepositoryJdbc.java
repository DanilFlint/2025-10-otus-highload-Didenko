package ru.didenko.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.didenko.repository.FriendRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryJdbc implements FriendRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public void addFriend(String currentUserId, String friendId) {
        String sql = "INSERT INTO friends (user_id, friend_id) " +
                "VALUES (:userId, :friendId) " +
                "ON CONFLICT DO NOTHING";

        namedParameterJdbcOperations.update(sql, new MapSqlParameterSource()
                .addValue("userId", currentUserId)
                .addValue("friendId", friendId));
    }

    public void removeFriend(String currentUserId, String friendId) {
        String sql = "DELETE FROM friends WHERE user_id = :userId AND friend_id = :friendId";

        namedParameterJdbcOperations.update(sql, new MapSqlParameterSource()
                .addValue("userId", currentUserId)
                .addValue("friendId", friendId));
    }

    @Override
    public List<Long> findFriendsIds(String userId) {
        String sql = "SELECT friend_id FROM friends WHERE user_id = :userId";

        return namedParameterJdbcOperations.query(sql, new MapSqlParameterSource()
                .addValue("userId", userId), (rs,rowNumber) -> rs.getLong("friend_id"));
    }
}
