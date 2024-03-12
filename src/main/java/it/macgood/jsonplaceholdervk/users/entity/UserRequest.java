package it.macgood.jsonplaceholdervk.users.entity;

import it.macgood.jsonplaceholdervk.users.entity.fields.Address;
import it.macgood.jsonplaceholdervk.users.entity.fields.Company;
import lombok.Builder;

@Builder
public record UserRequest(
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company
) {
}