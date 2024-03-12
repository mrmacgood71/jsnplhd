package it.macgood.jsonplaceholdervk.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoTestController {

    @GetMapping
    public ResponseEntity<GreetingsRestController.Greeting> getGreeting(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new GreetingsRestController.Greeting("Demo, %s!".formatted(user.getUsername())));
    }
}
