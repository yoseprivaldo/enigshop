package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.dto.request.PostRequest;
import com.enigmacamp.enigshop.entity.dto.response.PostResponse;
import com.enigmacamp.enigshop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceRestTemplate implements PostService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://jsonplaceholder.typicode.com";

    @Override
    public List<PostResponse> getAll() {
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(baseUrl + "/posts", HttpMethod.GET, null, new ParameterizedTypeReference<List<PostResponse>>() {
        });
        return response.getBody();
    }

    @Override
    public PostResponse add(PostRequest postRequest) {
        return null;
    }

    @Override
    public PostResponse getPostById(Integer id) {
        return restTemplate.getForObject(baseUrl + "/posts/" + id, PostResponse.class);
    }

    @Override
    public PostResponse updatePost(PostRequest postRequest, Integer id) {
        return null;
    }

    @Override
    public PostResponse patchPost(PostRequest postRequest, Integer id) {
        return null;
    }

    @Override
    public void deletePostById(Integer id) {

    }
}
