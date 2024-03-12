package it.macgood.jsonplaceholdervk.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greetings")
public class GreetingsRestController {

    @GetMapping
    public ResponseEntity<Greeting> getGreeting() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Greeting("Hello, %s!".formatted("user.getUsername()")));
    }

    @PutMapping
    public ResponseEntity<Greeting> getGreetings(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Greeting("Hello, %s!".formatted(user.getUsername())));
    }

    public record Greeting(String greeting) {
    }
}