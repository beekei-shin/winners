package org.winners.app.config.security;

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
public class AppSecurityWhitelist implements SecurityWhitelist, EnumClass {

    private final List<Whitelist> whitelist = List.of(
        new Whitelist("/v*/user/client/sign/**", Set.of(HttpMethod.POST)),
        new Whitelist("/v*/cert/phone-identity/**", Set.of(HttpMethod.POST, HttpMethod.PUT))
    );

}
