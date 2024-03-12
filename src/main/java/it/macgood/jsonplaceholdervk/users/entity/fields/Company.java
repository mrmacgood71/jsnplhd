package it.macgood.jsonplaceholdervk.users.entity.fields;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Company(
        String name,
        String catchPhrase,
        @JsonProperty("bs")
        String businessStrategy
) {}