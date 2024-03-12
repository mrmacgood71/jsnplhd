package it.macgood.jsonplaceholdervk.authentication;

import it.macgood.jsonplaceholdervk.users.entity.UserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationRestController {

    @PostMapping
    public void createUser(@RequestBody UserRequest request) {

    }
}
