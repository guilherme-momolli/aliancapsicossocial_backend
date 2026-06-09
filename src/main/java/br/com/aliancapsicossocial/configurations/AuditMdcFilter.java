package br.com.aliancapsicossocial.configurations;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuditMdcFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";
    private static final String CLIENT_IP = "clientIp";
    private static final String USERNAME = "username";
    private static final String SYSTEM_USER = "system";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            MDC.put(TRACE_ID, UUID.randomUUID().toString());
            MDC.put(CLIENT_IP, resolveClientIp(request));
            MDC.put(USERNAME, resolveCurrentUsername());

            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader(X_FORWARDED_FOR);
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String resolveCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            return SYSTEM_USER;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return normalize(userDetails.getUsername());
        }

        return normalize(authentication.getName());
    }

    private String normalize(String username) {
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            return SYSTEM_USER;
        }
        return username;
    }
}
