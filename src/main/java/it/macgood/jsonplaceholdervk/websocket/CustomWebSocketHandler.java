package it.macgood.jsonplaceholdervk.websocket;

import it.macgood.jsonplaceholdervk.utils.ApiConfigurationProperties;
import it.macgood.jsonplaceholdervk.websocket.audit.WebSocketAudit;
import it.macgood.jsonplaceholdervk.websocket.audit.WebSocketAuditOpenSessionRepository;
import it.macgood.jsonplaceholdervk.websocket.audit.WebSocketAuditService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomWebSocketHandler implements WebSocketHandler {

    WebSocketAuditOpenSessionRepository webSocketAuditOpenSessionRepository;
    WebSocketAuditService webSocketAuditService;
    ApiConfigurationProperties apiConfigurationProperties;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        var principal = session.getPrincipal();
        String username;
        if (principal != null) {
            username = principal.getName();
        } else {
            username = "Anonymous";
        }
        var audit = new WebSocketAudit();
        audit.setUsername(username);
        audit.setOpenTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        audit.setSessionId(session.getId());
        webSocketAuditOpenSessionRepository.addNewAudit(audit);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        var messageContent = message.getPayload().toString();

        WebSocketClient client = new StandardWebSocketClient();
        webSocketAuditOpenSessionRepository.addMessageToAudit(session.getId(), messageContent);

        WebSocketSession echoSession = client.doHandshake(
                new EchoWebSocketHandler(session),
                apiConfigurationProperties.getWebSocketEchoServerUrl()
        ).get();

        echoSession.sendMessage(new TextMessage(messageContent));
        webSocketAuditOpenSessionRepository.addResponseToAudit(
                session.getId(),
                (String) session.getAttributes().get("response"),
                (String) session.getAttributes().get("responseDate")
        );
        echoSession.close();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        webSocketAuditOpenSessionRepository.addResponseToAudit(
                session.getId(),
                (String) session.getAttributes().get("response"),
                (String) session.getAttributes().get("responseDate")
        );

        webSocketAuditOpenSessionRepository.setClosedTime(session.getId());
        webSocketAuditService.saveWebSocketAuditLog(webSocketAuditOpenSessionRepository.deleteAudit(session.getId()));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
