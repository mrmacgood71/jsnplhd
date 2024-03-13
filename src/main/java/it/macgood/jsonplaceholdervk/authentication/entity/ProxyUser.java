package it.macgood.jsonplaceholdervk.authentication.entity;

import lombok.Builder;

@Builder
public record ProxyUser (Integer id, String username, String password, String role) {
}
