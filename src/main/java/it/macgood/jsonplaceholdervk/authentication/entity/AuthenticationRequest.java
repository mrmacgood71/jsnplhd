package it.macgood.jsonplaceholdervk.authentication.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthenticationRequest(
            @NotNull
            String username,
            @NotNull
            String password,
            @NotNull
            @Pattern(
                    regexp = "ROLE_POSTS|ROLE_USERS|ROLE_ALBUMS|ROLE_POSTS_EDITOR|ROLE_POSTS_VIEWER|" +
                            "ROLE_USERS_EDITOR|ROLE_USERS_VIEWER|ROLE_ALBUMS_EDITOR|ROLE_ALBUMS_VIEWER",
                    message = "Invalid role"
            )
            String role
    ) { }
