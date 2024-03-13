package it.macgood.jsonplaceholdervk.websocket.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WebSocketAuditRepository {

    private final JdbcTemplate jdbcTemplate;
    public void save(WebSocketAudit audit) {
        String sql = "INSERT INTO t_audit (date_time, username, has_access, method, url) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, audit.getOpenTime(), audit.getUsername(), audit.getId(), audit.getSessionId(), audit.getCloseTime());
    }
}
