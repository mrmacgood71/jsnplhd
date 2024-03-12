package it.macgood.jsonplaceholdervk.albums.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.macgood.jsonplaceholdervk.albums.entity.Album;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumResponse;
import it.macgood.jsonplaceholdervk.config.ApiConfigurationProperties;
import it.macgood.jsonplaceholdervk.photos.Photo;
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
public class AlbumsServiceImpl implements AlbumsService {
    private final ApiConfigurationProperties apiProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public AlbumsServiceImpl(
            @Qualifier("apiConfigurationProperties") ApiConfigurationProperties apiProperties,
            RestTemplate restTemplate
    ) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable("albums")
    public List<AlbumResponse> getAlbums() {
        ResponseEntity<List<AlbumResponse>> response = restTemplate.exchange(
                apiProperties.getAlbumsUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Override
    @Cacheable(value = "albums", key = "#id")
    public Optional<AlbumResponse> getAlbumById(String id) {
        var response =
                restTemplate.getForEntity(apiProperties.getAlbumsUrl() + "/%s".formatted(id), AlbumResponse.class);
        return Optional.of(response.getBody());
    }

    @Override
    @Cacheable(value = "albums", key = "#userId")
    public List<AlbumResponse> getAlbumsByUserId(String userId) {
        var response = restTemplate.getForEntity(
                apiProperties.getAlbumsUrl() + "?userId=%s".formatted(userId),
                String.class
        );

        try {
            return mapper.readerForListOf(AlbumResponse.class).<ArrayList<AlbumResponse>>readValue(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Cacheable(value = "photos", key = "#albumId")
    public List<Photo> getPhotosByAlbumId(String albumId) {
        var response = restTemplate.getForEntity(
                apiProperties.getAlbumsUrl() + "/%s".formatted(albumId) + "/photos",
                String.class
        );

        try {
            return mapper.readerForListOf(Photo.class).<ArrayList<Photo>>readValue(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @CachePut(value = "albums", key = "#result.id")
    public AlbumResponse sendAlbum(Album album) {
        return restTemplate.postForObject(apiProperties.getAlbumsUrl(), album, AlbumResponse.class);
    }

    @Override
    @CachePut(value = "albums", key = "#album.id")
    public Album putAlbum(String albumId, Album album) {
        restTemplate.put(apiProperties.getAlbumsUrl() + "/%s".formatted(albumId), album);
        return album;
    }

    @Override
    @CachePut(value = "albums", key = "#albumDetails.id")
    public AlbumResponse patchAlbum(String albumId, Map<String, Object> albumDetails) {
        return restTemplate.patchForObject(
                apiProperties.getAlbumsUrl() + "/%s".formatted(albumId),
                albumDetails,
                AlbumResponse.class
        );
    }

    @Override
    @CacheEvict(value = "albums", key = "#albumId")
    public void deleteAlbum(String albumId) {
        restTemplate.delete(apiProperties.getAlbumsUrl() + "/%s".formatted(albumId));
    }
}
