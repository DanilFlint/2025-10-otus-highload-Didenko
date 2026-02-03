package ru.didenko.service;

import java.util.List;

public interface FriendService {
    void setFriend(String currentUserId, String targetUserId);

    void deleteFriend(String currentUserId, String targetUserId);

    List<Long> getFriendsIds(String userId);
}
