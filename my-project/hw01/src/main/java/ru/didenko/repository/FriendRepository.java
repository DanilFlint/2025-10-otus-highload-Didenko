package ru.didenko.repository;

import java.util.List;

public interface FriendRepository {

    void addFriend(String currentUserId, String friendId);

    void removeFriend(String currentUserId, String friendId);

    List<Long> findFriendsIds(String userId);
}
