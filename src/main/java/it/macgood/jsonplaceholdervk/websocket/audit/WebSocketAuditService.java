package it.macgood.jsonplaceholdervk.websocket.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketAuditService {
    private final WebSocketAuditRepository auditRepository;

    public void saveWebSocketAuditLog(final WebSocketAudit log) {
        auditRepository.save(log);
    }
}
