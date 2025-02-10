package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.entity.dto.request.PostRequest;
import com.enigmacamp.enigshop.entity.dto.response.PostResponse;
import com.enigmacamp.enigshop.service.impl.PostServiceRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostRestClientController {
    private final PostServiceRestClient postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Integer id) {
        PostResponse post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostResponse> addPost(@RequestBody PostRequest postRequest) {
        PostResponse createdPost = postService.add(postRequest);
        return ResponseEntity.status(201).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Integer id, @RequestBody PostRequest postRequest) {
        PostResponse updatedPost = postService.updatePost(postRequest, id);
        return ResponseEntity.ok(updatedPost);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> patchPost(@PathVariable Integer id, @RequestBody PostRequest postRequest) {
        PostResponse patchedPost = postService.patchPost(postRequest, id);
        return ResponseEntity.ok(patchedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

}
