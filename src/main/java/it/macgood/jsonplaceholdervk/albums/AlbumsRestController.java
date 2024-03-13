package it.macgood.jsonplaceholdervk.albums;

import it.macgood.jsonplaceholdervk.albums.entity.AlbumMapper;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumRequest;
import it.macgood.jsonplaceholdervk.albums.entity.AlbumResponse;
import it.macgood.jsonplaceholdervk.albums.service.AlbumsService;
import it.macgood.jsonplaceholdervk.albums.entity.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumsRestController {

    private final AlbumsService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlbumResponse>> getAlbums(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok()
                .body(service.getAlbums());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumResponse> getAlbumById(@PathVariable String id) {
        Optional<AlbumResponse> album = service.getAlbumById(id);
        return album.map(albumResponse -> ResponseEntity.ok()
                .body(albumResponse)).orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @GetMapping(value = "by-userId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlbumResponse>> getAlbumByUserId(@PathVariable String userId) {
        var albums = service.getAlbumsByUserId(userId);
        return ResponseEntity.ok(albums);
    }

    @GetMapping(value = "/{id}/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Photo>> getPhotosByAlbumId(@PathVariable String id) {
        return ResponseEntity.ok(service.getPhotosByAlbumId(id));
    }

    @PostMapping
    public void sendAlbum(@RequestBody AlbumRequest request) {
        service.sendAlbum(AlbumMapper.toAlbum(request));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  putAlbum(
            @PathVariable String id,
            @RequestBody AlbumRequest request
    ) {
        service.putAlbum(id, AlbumMapper.toAlbum(request));
        return ResponseEntity.ok().build();

    }
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumResponse> patchAlbum(
            @PathVariable String id,
            @RequestBody Map<String, Object> request
    ) {
        return ResponseEntity.ok(service.patchAlbum(id, request));

    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAlbum(@PathVariable String id) {
        service.deleteAlbum(id);
        return ResponseEntity.ok().build();
    }


}


