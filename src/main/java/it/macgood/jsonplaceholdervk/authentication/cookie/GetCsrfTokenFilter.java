package it.macgood.jsonplaceholdervk.authentication.cookie;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Setter
public class GetCsrfTokenFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcherCsrf = new AntPathRequestMatcher("/csrf", HttpMethod.GET.name());
    private RequestMatcher requestMatcherAuth = new AntPathRequestMatcher("/api/v1/auth", HttpMethod.POST.name());

    private CsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (this.requestMatcherCsrf.matches(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            this.objectMapper.writeValue(response.getWriter(), this.csrfTokenRepository.loadDeferredToken(request, response).get());
            return;
        } else if (this.requestMatcherAuth.matches(request)) {
            System.out.println("GetCsrfTokenFilter.doFilterInternal");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            this.objectMapper.writeValue(response.getWriter(), this.csrfTokenRepository.loadDeferredToken(request, response).get());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
