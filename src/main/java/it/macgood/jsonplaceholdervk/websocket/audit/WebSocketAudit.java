package it.macgood.jsonplaceholdervk.websocket.audit;

import it.macgood.jsonplaceholdervk.websocket.WebSocketMessage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebSocketAudit {
    Long id;
    String sessionId;
    String openTime;
    String closeTime;
    String username;
    Set<WebSocketMessage> messages;
    Set<WebSocketMessage> responses;
    public boolean addMessage(String message) {
        var webSocketMessage = new WebSocketMessage(
                message,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return messages.add(webSocketMessage);
    }

    public boolean addResponse(String response, String time) {
        return responses.add(new WebSocketMessage(response, time));
    }

}
