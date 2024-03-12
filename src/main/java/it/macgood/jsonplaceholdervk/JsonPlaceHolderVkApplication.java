package it.macgood.jsonplaceholdervk;

import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import it.macgood.jsonplaceholdervk.authentication.config.TokenCookieAuthenticationConfigurer;
import it.macgood.jsonplaceholdervk.authentication.cookie.TokenCookieJweStringDeserializer;
import it.macgood.jsonplaceholdervk.authentication.cookie.TokenCookieJweStringSerializer;
import it.macgood.jsonplaceholdervk.config.ApiConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;
import org.apache.hc.client5.http.impl.classic.HttpClients;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(ApiConfigurationProperties.class)
public class JsonPlaceHolderVkApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonPlaceHolderVkApplication.class, args);
    }

    @Bean
    public TokenCookieJweStringSerializer tokenCookieJweStringSerializer(
            @Value("${jwt.cookie-token-key}") String cookieTokenKey
    ) throws Exception {
        return new TokenCookieJweStringSerializer(new DirectEncrypter(
                OctetSequenceKey.parse(cookieTokenKey)
        ));
    }

    @Bean
    public RestTemplate restTemplate() {
        var httpClient = HttpClients.createDefault();
        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }

    @Bean
    public TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer(
            @Value("${jwt.cookie-token-key}") String cookieTokenKey,
            JdbcTemplate jdbcTemplate
    ) throws Exception {
        return new TokenCookieAuthenticationConfigurer()
                .tokenCookieStringDeserializer(new TokenCookieJweStringDeserializer(
                        new DirectDecrypter(
                                OctetSequenceKey.parse(cookieTokenKey)
                        )
                ))
                .jdbcTemplate(jdbcTemplate);
    }


    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        return username -> jdbcTemplate.query("select * from t_user where c_username = ?",
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
    }
}
