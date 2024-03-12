package it.macgood.jsonplaceholdervk.users.entity.fields;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Geolocation(
        @JsonProperty("lat")
        String latitude,
        @JsonProperty("lng")
        String longitude
) {}
