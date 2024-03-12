package it.macgood.jsonplaceholdervk.users;

import it.macgood.jsonplaceholdervk.posts.entity.PostResponse;
import it.macgood.jsonplaceholdervk.users.entity.UserMapper;
import it.macgood.jsonplaceholdervk.users.entity.UserRequest;
import it.macgood.jsonplaceholdervk.users.entity.UserResponse;
import it.macgood.jsonplaceholdervk.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersRestController {

    private final UsersService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok().body(service.getUsers());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUsersById(@PathVariable String id) {
        Optional<UserResponse> album = service.getUsersById(id);
        return album.map(userResponse -> ResponseEntity.ok()
                .body(userResponse)).orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @GetMapping(value = "by-postId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponse>> getUsersByPostId(@PathVariable String userId) {
        var albums = service.getPostsByUserId(userId);
        return ResponseEntity.ok(albums);
    }



    @PostMapping
    public void sendAlbum(@RequestBody UserRequest request) {
        service.sendUser(UserMapper.toUser(request));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  putUser(
            @PathVariable String id,
            @RequestBody UserRequest request
    ) {
        service.putUser(id, UserMapper.toUser(request));
        return ResponseEntity.ok().build();

    }
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> patchUser(
            @PathVariable String id,
            @RequestBody Map<String, Object> request
    ) {
        return ResponseEntity.ok(service.patchUser(id, request));

    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
