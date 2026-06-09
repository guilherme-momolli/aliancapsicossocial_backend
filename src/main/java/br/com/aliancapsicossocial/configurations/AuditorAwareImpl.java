package br.com.aliancapsicossocial.configurations;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String SYSTEM_AUDITOR = "system";

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || isAnonymous(authentication)) {
            return Optional.of(SYSTEM_AUDITOR);
        }

        return Optional.of(resolveUsername(authentication));
    }

    private boolean isAnonymous(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return principal == null || "anonymousUser".equals(principal);
    }

    private String resolveUsername(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return normalizeUsername(userDetails.getUsername());
        }

        return normalizeUsername(authentication.getName());
    }

    private String normalizeUsername(String username) {
        if (username == null || username.isBlank()) {
            return SYSTEM_AUDITOR;
        }
        return username;
    }
}
