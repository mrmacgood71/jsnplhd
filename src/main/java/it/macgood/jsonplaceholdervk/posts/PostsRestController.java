package it.macgood.jsonplaceholdervk.posts;

import it.macgood.jsonplaceholdervk.albums.entity.AlbumResponse;
import it.macgood.jsonplaceholdervk.posts.entity.Comment;
import it.macgood.jsonplaceholdervk.posts.entity.PostMapper;
import it.macgood.jsonplaceholdervk.posts.entity.PostRequest;
import it.macgood.jsonplaceholdervk.posts.entity.PostResponse;
import it.macgood.jsonplaceholdervk.posts.service.PostsService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostsRestController {

    private final PostsService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponse>> getPosts(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok()
                .body(service.getPosts());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> getPostById(@PathVariable String id) {
        Optional<PostResponse> postResponse = service.getPostById(id);
        return postResponse.map(albumResponse -> ResponseEntity.ok()
                .body(albumResponse)).orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @GetMapping(value = "by-userId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@PathVariable String userId) {
        var posts = service.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(value = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable String id) {
        return ResponseEntity.ok(service.getCommentsByPostId(id));
    }

    @PostMapping
    public void sendAlbum(@RequestBody PostRequest request) {
        service.sendPost(PostMapper.toPost(request));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putPost(
            @PathVariable String id,
            @RequestBody PostRequest request
    ) {
        service.putPost(id, PostMapper.toPost(request));
        return ResponseEntity.ok().build();

    }
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> patchPost(
            @PathVariable String id,
            @RequestBody Map<String, Object> request
    ) {
        return ResponseEntity.ok(service.patchPost(id, request));

    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        service.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
