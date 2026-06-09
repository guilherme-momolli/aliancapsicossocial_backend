package br.com.aliancapsicossocial.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityAuditEventListener {

    private static final Logger SECURITY_AUDIT = LoggerFactory.getLogger("SECURITY_AUDIT");
    private static final String SYSTEM_USER = "system";

    @Async
    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        SECURITY_AUDIT.info(
                "event=AUTHENTICATION_SUCCESS username={} authenticated={} authorities={}",
                resolveUsername(authentication),
                authentication != null && authentication.isAuthenticated(),
                authentication == null ? "[]" : authentication.getAuthorities()
        );
    }

    @Async
    @EventListener
    public void handleAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        Authentication authentication = event.getAuthentication();
        SECURITY_AUDIT.warn(
                "event=AUTHENTICATION_FAILURE username={} exception={} message={}",
                resolveUsername(authentication),
                event.getException().getClass().getSimpleName(),
                event.getException().getMessage()
        );
    }

    private String resolveUsername(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
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
