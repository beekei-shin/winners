package com.winners.backofficeApi.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.winners.core.config.security.filter.SecurityWhitelist;
import org.winners.core.config.security.filter.Whitelist;
import org.winners.core.domain.common.EnumClass;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class BackofficeSecurityWhitelist implements SecurityWhitelist, EnumClass {

    private final List<Whitelist> whitelist = List.of(
        new Whitelist("/v*/**", Set.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE))
    );

}
