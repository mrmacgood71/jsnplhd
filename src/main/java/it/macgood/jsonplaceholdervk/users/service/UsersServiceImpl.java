package it.macgood.jsonplaceholdervk.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.macgood.jsonplaceholdervk.utils.ApiConfigurationProperties;
import it.macgood.jsonplaceholdervk.posts.entity.PostResponse;
import it.macgood.jsonplaceholdervk.users.entity.User;
import it.macgood.jsonplaceholdervk.users.entity.UserResponse;
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
public class UsersServiceImpl implements UsersService {
    private final ApiConfigurationProperties apiProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public UsersServiceImpl(
            @Qualifier("apiConfigurationProperties") ApiConfigurationProperties apiProperties,
            RestTemplate restTemplate
    ) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable("users")
    public List<UserResponse> getUsers() {
        ResponseEntity<List<UserResponse>> response = restTemplate.exchange(
                apiProperties.getUsersUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public Optional<UserResponse> getUsersById(String id) {
        var response = restTemplate.getForEntity(apiProperties.getUsersUrl() + "/%s".formatted(id), UserResponse.class);
        return Optional.of(response.getBody());
    }

    @Override
    @Cacheable(value = "posts", key = "#userId")
    public List<PostResponse> getPostsByUserId(String userId) {
        var response = restTemplate.getForEntity(apiProperties.getUsersUrl() + "/%s/posts".formatted(userId), String.class);
        try {
            return mapper.readerForListOf(PostResponse.class).<ArrayList<PostResponse>>readValue(response.getBody());
        } catch (JsonProcessingException e) {throw new RuntimeException(e);}
    }

    @Override
    @CachePut(value = "users", key = "#result.id")
    public UserResponse sendUser(User user) {
        return restTemplate.postForObject(apiProperties.getUsersUrl(), user, UserResponse.class);
    }

    @Override
    @CachePut(value = "users", key = "#user.id")
    public void putUser(String userId, User user) {
        restTemplate.put(apiProperties.getUsersUrl() + "/%s".formatted(userId), user);
    }

    @Override
    @CachePut(value = "users", key = "#userDetails.id")
    public UserResponse patchUser(String userId, Map<String, Object> userDetails) {
        return restTemplate.patchForObject(apiProperties.getUsersUrl() + "/%s".formatted(userId), userDetails, UserResponse.class);
    }

    @Override
    @CacheEvict(value = "users", key = "#userId")
    public void deleteUser(String userId) {
        restTemplate.delete(apiProperties.getUsersUrl() + "/%s".formatted(userId));
    }
}
