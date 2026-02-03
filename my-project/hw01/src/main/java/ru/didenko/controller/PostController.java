package ru.didenko.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.didenko.domain.Post;
import ru.didenko.domain.PostDTO;
import ru.didenko.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserDetailsService userDetailsService;


    @PostMapping("/post/create")
    public Long addPost(@RequestBody PostDTO post, @AuthenticationPrincipal UserDetails userDetails) {
        Post newPost = Post.builder()
                .authorUserId(Long.parseLong(userDetails.getUsername()))
                .text(post.getText())
                .build();

        return postService.insert(newPost);
    }

    @PutMapping("/post/update/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostDTO post, @AuthenticationPrincipal UserDetails userDetails) {
        Post newPost = Post.builder()
                .id(id)
                .authorUserId(Long.parseLong(userDetails.getUsername()))
                .text(post.getText())
                .build();

        return postService.update(newPost);
    }

    @GetMapping("/post/feed")
    public List<Post> getPage(@RequestParam Long offset, @RequestParam Long limit) {
        return postService.getPage(offset, limit);
    }

    @GetMapping("/post/get/{id}")
    public Post getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @PutMapping("/post/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        postService.deleteById(id);
    }

}
