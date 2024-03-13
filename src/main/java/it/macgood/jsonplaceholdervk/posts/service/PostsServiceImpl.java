package it.macgood.jsonplaceholdervk.posts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumResponse;
import it.macgood.jsonplaceholdervk.utils.ApiConfigurationProperties;
import it.macgood.jsonplaceholdervk.posts.entity.Comment;
import it.macgood.jsonplaceholdervk.posts.entity.Post;
import it.macgood.jsonplaceholdervk.posts.entity.PostResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostsServiceImpl implements PostsService {
    private final ApiConfigurationProperties apiProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public PostsServiceImpl(
            @Qualifier("apiConfigurationProperties") ApiConfigurationProperties apiProperties,
            RestTemplate restTemplate
    ) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable("posts")
    public List<PostResponse> getPosts() {
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
                apiProperties.getPostsUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Override
    @Cacheable(value = "posts", key = "#id")
    public Optional<PostResponse> getPostById(String id) {
        var response =
                restTemplate.getForEntity(apiProperties.getPostsUrl() + "/%s".formatted(id), PostResponse.class);
        return Optional.of(response.getBody());
    }

    @Override
    public List<PostResponse> getPostsByUserId(String userId) {
        var response = restTemplate.getForEntity(
                apiProperties.getPostsUrl() + "?userId=%s".formatted(userId),
                String.class
        );

        try {
            return mapper.readerForListOf(PostResponse.class).<ArrayList<PostResponse>>readValue(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comment> getCommentsByPostId(String postId) {
        var response = restTemplate.getForEntity(
                apiProperties.getPostsUrl() + "/%s".formatted(postId) + "/comments",
                String.class
        );

        try {
            return mapper.readerForListOf(Comment.class).<ArrayList<Comment>>readValue(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @CachePut(value = "posts", key = "#result.id")
    public PostResponse sendPost(Post post) {
        return restTemplate.postForObject(apiProperties.getPostsUrl(), post, PostResponse.class);
    }

    @Override
    @CachePut(value = "posts", key = "#postId")
    public void putPost(String postId, Post post) {
        restTemplate.put(apiProperties.getPostsUrl() + "/%s".formatted(postId), post);
    }

    @Override
    @CachePut(value = "posts", key = "#result.id")
    public PostResponse patchPost(String postId, Map<String, Object> postDetails) {
        return restTemplate.patchForObject(
                apiProperties.getPostsUrl() + "/%s".formatted(postId),
                postDetails,
                PostResponse.class
        );
    }

    @Override
    @CacheEvict(value = "posts", key = "#postId")
    public void deletePost(String postId) {
        restTemplate.delete(apiProperties.getPostsUrl() + "/%s".formatted(postId));
    }
}
