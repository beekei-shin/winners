package com.winners.backofficeApi.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.winners.core.config.security.token.TokenRole;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(annotation.userId(), null,
            Arrays.stream(annotation.authorities())
                .map(TokenRole::getRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())));
        return context;
    }

}
