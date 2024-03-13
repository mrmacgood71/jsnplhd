package it.macgood.jsonplaceholdervk.albums.service;

import it.macgood.jsonplaceholdervk.albums.entity.Album;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumResponse;
import it.macgood.jsonplaceholdervk.albums.entity.Photo;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface AlbumsService {

    List<AlbumResponse> getAlbums();
    Optional<AlbumResponse> getAlbumById(String id);
    List<AlbumResponse> getAlbumsByUserId(String userId);
    List<Photo> getPhotosByAlbumId(String albumId);
    AlbumResponse sendAlbum(Album album);
    Album putAlbum(String albumId, Album album);
    AlbumResponse patchAlbum(String albumId, Map<String, Object> albumDetails);

    void deleteAlbum(String albumId);


}
