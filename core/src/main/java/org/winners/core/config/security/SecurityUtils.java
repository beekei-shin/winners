package org.winners.core.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.winners.core.config.exception.UnauthorizedTokenException;

import java.util.Optional;

public class SecurityUtils {

    public static long getTokenUserId() {
        return getTokenUserIdOptional()
            .orElseThrow(UnauthorizedTokenException::new);
    }

    public static Optional<Long> getTokenUserIdOptional() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .filter(principal -> !principal.toString().equals("anonymousUser"))
            .map(principal -> Long.parseLong(String.valueOf(principal)));
    }

}
