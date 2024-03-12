package it.macgood.jsonplaceholdervk.users.entity.fields;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        @JsonProperty("geo")
        Geolocation geolocation
) {}
