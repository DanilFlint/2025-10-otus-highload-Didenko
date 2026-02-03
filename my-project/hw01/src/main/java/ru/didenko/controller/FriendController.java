package ru.didenko.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.didenko.service.impl.FriendServiceImpl;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendServiceImpl friendServiceImpl;

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PutMapping("/set/{user_id}")
    public ResponseEntity<Void> setFriend(@PathVariable("user_id") String friendId) {
        try {
            friendServiceImpl.setFriend(getCurrentUserId(), friendId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/delete/{user_id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable("user_id") String friendId) {
        friendServiceImpl.deleteFriend(getCurrentUserId(), friendId);
        return ResponseEntity.ok().build();
    }
}
