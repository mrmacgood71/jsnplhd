package it.macgood.jsonplaceholdervk.websocket.audit;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class WebSocketAuditOpenSessionRepository {
    private ConcurrentHashMap<String, WebSocketAudit> audits = new ConcurrentHashMap<>();

    public WebSocketAudit addNewAudit(WebSocketAudit audit) {
        return audits.put(audit.getSessionId(), audit);
    }

    public boolean addMessageToAudit(String session, String message) {
        return audits.get(session).addMessage(message);
    }
    public boolean addResponseToAudit(String session, String response, String time) {
       return audits.get(session).addResponse(response, time);
    }

    public void setClosedTime(String session) {
        audits.get(session).setCloseTime(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public WebSocketAudit deleteAudit(String session) {
        WebSocketAudit audit = audits.get(session);
        audits.remove(session);
        return audit;
    }
}
