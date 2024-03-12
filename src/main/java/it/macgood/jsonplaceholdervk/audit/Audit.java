package it.macgood.jsonplaceholdervk.audit;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Audit {

    private UUID id;

    private LocalDateTime dateTime;

    private String username;

    private boolean hasAccess;

    private String method;

    private String url;
}
