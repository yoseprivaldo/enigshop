package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.dto.request.PostRequest;
import com.enigmacamp.enigshop.entity.dto.response.PostResponse;
import com.enigmacamp.enigshop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceRestClient implements PostService {

    private final RestClient restClient;

    @Override
    public List<PostResponse> getAll() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<List<PostResponse>>() {});
    }

    @Override
    public PostResponse add(PostRequest postRequest) {
        return restClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .body(PostResponse.class);
    }

    @Override
    public PostResponse getPostById(Integer id) {
        return restClient.get()
                .uri("/post/" +  id)
                .retrieve()
                .body(PostResponse.class);
    }

    @Override
    public PostResponse updatePost(PostRequest postRequest, Integer id) {
        return restClient.put()
                .uri("/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .body(PostResponse.class);
    }

    @Override
    public PostResponse patchPost(PostRequest postRequest, Integer id) {
        return restClient.post()
                .uri("posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .body(PostResponse.class);
    }

    @Override
    public void deletePostById(Integer id) {
        restClient.delete()
                .uri("/posts/" + id)
                .retrieve()
                .body(Void.class);
    }
}
