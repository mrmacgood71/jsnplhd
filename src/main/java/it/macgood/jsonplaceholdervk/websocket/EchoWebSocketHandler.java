package it.macgood.jsonplaceholdervk.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.*;
import org.springframework.web.socket.WebSocketMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class EchoWebSocketHandler implements WebSocketHandler {

    private final WebSocketSession clientSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {}

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String responseMessage = message.getPayload().toString();
        if (!responseMessage.startsWith("Request served by")) {
            clientSession.sendMessage(new TextMessage(responseMessage));
            clientSession.getAttributes().put("response", responseMessage);
            clientSession.getAttributes().put(
                    "responseDate",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {}

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
