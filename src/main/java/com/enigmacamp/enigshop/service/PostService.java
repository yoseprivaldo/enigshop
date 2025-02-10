package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.dto.request.PostRequest;
import com.enigmacamp.enigshop.entity.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    List<PostResponse> getAll();
    PostResponse add(PostRequest postRequest);
    PostResponse getPostById(Integer id);
    PostResponse updatePost(PostRequest postRequest, Integer id);
    PostResponse patchPost(PostRequest postRequest, Integer id);
    void deletePostById(Integer id);
}
