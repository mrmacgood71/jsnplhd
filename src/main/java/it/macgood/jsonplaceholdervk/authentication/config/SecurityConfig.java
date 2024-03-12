package it.macgood.jsonplaceholdervk.authentication.config;

import it.macgood.jsonplaceholdervk.audit.AuditRepository;
import it.macgood.jsonplaceholdervk.audit.RequestAuditFilter;
import it.macgood.jsonplaceholdervk.authentication.cookie.GetCsrfTokenFilter;
import it.macgood.jsonplaceholdervk.authentication.cookie.TokenCookieJweStringSerializer;
import it.macgood.jsonplaceholdervk.authentication.cookie.TokenCookieSessionAuthenticationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html,",
//            "/api/proxy/add_user/"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer,
            TokenCookieJweStringSerializer tokenCookieJweStringSerializer,
            AuditRepository auditRepository
    ) throws Exception {
        var tokenCookieSessionAuthenticationStrategy = new TokenCookieSessionAuthenticationStrategy();
        tokenCookieSessionAuthenticationStrategy.setTokenStringSerializer(tokenCookieJweStringSerializer);

        RequestAuditFilter filter = new RequestAuditFilter(auditRepository);
        http.httpBasic(Customizer.withDefaults())
                .addFilterAfter(new GetCsrfTokenFilter(), ExceptionTranslationFilter.class)
                .addFilterBefore(filter, ExceptionTranslationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers("/api/demo/**").permitAll()
                                .requestMatchers("/api/v1/posts/**").hasAnyRole("ADMIN", "POSTS")
                                .requestMatchers("/api/v1/users/**").hasAnyRole("ADMIN", "USERS")
                                .requestMatchers("/api/v1/albums/**").hasAnyRole("ADMIN", "ALBUMS")
                                .requestMatchers("/error", "index.html").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionAuthenticationStrategy(tokenCookieSessionAuthenticationStrategy))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy((authentication, request, response) -> {}));

        http.apply(tokenCookieAuthenticationConfigurer);

        return http.build();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
                ROLE_ADMIN > ROLE_POSTS
                ROLE_ADMIN > ROLE_USERS
                ROLE_ADMIN > ROLE_ALBUMS
                ROLE_POSTS > ROLE_POSTS_EDITOR > ROLE_POSTS_VIEWER
                ROLE_USERS > ROLE_USERS_EDITOR > ROLE_USERS_VIEWER
                ROLE_ALBUMS > ROLE_ALBUMS_EDITOR > ROLE_ALBUMS_VIEWER"""
        );
        return hierarchy;
    }
}
