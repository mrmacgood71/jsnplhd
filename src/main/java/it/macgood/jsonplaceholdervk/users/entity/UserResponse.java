package it.macgood.jsonplaceholdervk.users.entity;

import it.macgood.jsonplaceholdervk.users.entity.fields.Address;
import it.macgood.jsonplaceholdervk.users.entity.fields.Company;

public record UserResponse(
        Integer id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company
) {
}
