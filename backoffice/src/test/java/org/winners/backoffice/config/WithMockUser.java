package org.winners.backoffice.config;

import org.springframework.security.test.context.support.WithSecurityContext;
import org.winners.core.config.security.token.TokenRole;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockUser {
    long userId() default 1L;
    TokenRole[] authorities();
}
