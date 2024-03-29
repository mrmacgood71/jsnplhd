package it.macgood.jsonplaceholdervk.authentication;

import it.macgood.jsonplaceholdervk.authentication.entity.AuthenticationRequest;
import it.macgood.jsonplaceholdervk.users.entity.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationRestController {

    private final AuthenticationRepository repository;
    @GetMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody AuthenticationRequest request) {
        try {
            repository.auth(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
