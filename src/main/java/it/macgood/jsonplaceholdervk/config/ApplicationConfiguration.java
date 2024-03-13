package it.macgood.jsonplaceholdervk.config;

import it.macgood.jsonplaceholdervk.authentication.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final AuthenticationRepository authenticationRepository;
    @Bean
    public RestTemplate restTemplate() {
        var httpClient = HttpClients.createDefault();
        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        System.out.println("ApplicationConfiguration.userDetailsService");
        return username -> {
            UserDetails userDetails = jdbcTemplate.query("select * from t_user where c_username = ?",
                            (rs, i) -> User.builder()
                                    .username(rs.getString("c_username"))
                                    .password(rs.getString("c_password"))
                                    .authorities(
                                            jdbcTemplate.query("select c_authority from t_user_authority where id_user = ?",
                                                    (rs1, i1) ->
                                                            new SimpleGrantedAuthority(rs1.getString("c_authority")),
                                                    rs.getInt("id")))
                                    .build(), username)
                    .stream()
                    .findFirst()
                    .orElse(null);

//            if (username == null) {
//                authenticationRepository.insertUser()
//            }

            return userDetails;
        };
    }
}
