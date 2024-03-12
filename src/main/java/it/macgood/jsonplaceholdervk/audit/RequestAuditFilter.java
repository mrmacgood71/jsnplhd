package it.macgood.jsonplaceholdervk.audit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestAuditFilter extends OncePerRequestFilter {

    private final AuditRepository auditRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Object auditSaved = request.getAttribute("auditSaved");
        if (auditSaved == null) {
            var audit = new Audit();
            audit.setDateTime(LocalDateTime.now());
            audit.setMethod(request.getMethod());
            audit.setUrl(request.getRequestURI());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                audit.setUsername(authentication.getName());
            }

            int status = response.getStatus();
            audit.setHasAccess(status != HttpServletResponse.SC_UNAUTHORIZED && status != HttpServletResponse.SC_FORBIDDEN);

            auditRepository.save(audit);
            request.setAttribute("auditSaved", true);
            log.info(audit.toString());
        }

        filterChain.doFilter(request, response);
    }
}