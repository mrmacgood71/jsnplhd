package it.macgood.jsonplaceholdervk.authentication;

import it.macgood.jsonplaceholdervk.authentication.entity.AuthenticationRequest;
import it.macgood.jsonplaceholdervk.authentication.entity.ProxyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationRepository {
    private final JdbcTemplate jdbcTemplate;

    public void auth(AuthenticationRequest request) {
        ProxyUser user = insertUser(request.username(), request.password());;
        insertUserAuthority(user.id(), request.role());
    }

    public ProxyUser insertUser(String username, String password) {
        Integer id = jdbcTemplate.queryForObject("SELECT MAX(id) from t_user", Integer.class);
        String sql = "INSERT INTO t_user (id, c_username, c_password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, (id + 1), username, "{noop}" + password);
        var user = jdbcTemplate.query("select * from t_user where c_username = ?",
                        (rs, i) -> ProxyUser.builder()
                                        .id(rs.getInt(1))
                                        .username(rs.getString("c_username"))
                                        .password(rs.getString("c_password"))
                                        .build(), username
                )
                .stream()
                .findFirst()
                .orElse(null);
        return user;
    }

    public void insertUserAuthority(int userId, String authority) {
        System.out.println(authority);
        String sql = "INSERT INTO t_user_authority (id, id_user, c_authority) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, userId, authority);
    }


}
