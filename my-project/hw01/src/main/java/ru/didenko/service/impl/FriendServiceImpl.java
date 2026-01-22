package ru.didenko.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.didenko.repository.FriendRepository;
import ru.didenko.service.FriendService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;

    public void setFriend(String currentUserId, String targetUserId) {
        if (currentUserId.equals(targetUserId)) {
            throw new IllegalArgumentException("Вы не можете добавить самого себя в друзья");
        }
        friendRepository.addFriend(currentUserId, targetUserId);
    }

    public void deleteFriend(String currentUserId, String targetUserId) {
        friendRepository.removeFriend(currentUserId, targetUserId);
    }

    @Override
    public List<Long> getFriendsIds(String userId) {
        return friendRepository.findFriendsIds(userId);
    }
}
