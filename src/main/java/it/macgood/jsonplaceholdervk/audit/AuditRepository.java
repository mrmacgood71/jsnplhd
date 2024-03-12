package it.macgood.jsonplaceholdervk.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuditRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Audit audit) {
        String sql = "INSERT INTO t_audit (date_time, username, has_access, method, url) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, audit.getDateTime(), audit.getUsername(), audit.isHasAccess(), audit.getMethod(), audit.getUrl());
    }
}
