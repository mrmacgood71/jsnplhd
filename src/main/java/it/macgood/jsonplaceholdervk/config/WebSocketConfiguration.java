package it.macgood.jsonplaceholdervk.config;

import it.macgood.jsonplaceholdervk.utils.ApiConfigurationProperties;
import it.macgood.jsonplaceholdervk.websocket.CustomWebSocketHandler;
import it.macgood.jsonplaceholdervk.websocket.audit.WebSocketAuditOpenSessionRepository;
import it.macgood.jsonplaceholdervk.websocket.audit.WebSocketAuditService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    WebSocketAuditOpenSessionRepository webSocketAuditOpenSessionRepository;
    WebSocketAuditService webSocketAuditService;
    ApiConfigurationProperties apiConfigurationProperties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(
                new CustomWebSocketHandler(
                        webSocketAuditOpenSessionRepository,
                        webSocketAuditService,
                        apiConfigurationProperties
                ),
                "/ws"
        ).setAllowedOrigins("*");
    }
}