package it.macgood.jsonplaceholdervk.albums.entity;

public class AlbumMapper {
    public static AlbumRequest toAlbumRequest(Album album) {
        return new AlbumRequest(album.userId(), album.title(), album.id());
    }

    public static Album toAlbum(AlbumRequest request) {
        return new Album(request.userId(), request.id(), request.title());
    }
    public static AlbumResponse toAlbumResponse(Album album) {
        return new AlbumResponse(album.userId(), album.id(), album.title());
    }
}
