package org.winners.core.config.security.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Whitelist {
    private final String path;
    private final Set<HttpMethod> methods;
}
