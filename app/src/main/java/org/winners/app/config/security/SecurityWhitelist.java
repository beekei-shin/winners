package org.winners.app.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.winners.core.domain.common.EnumClass;

import java.util.Arrays;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum SecurityWhitelist implements EnumClass {

    USER_SIGN("/v*/user/client/sign/**", Set.of(HttpMethod.POST), true),
    CERT_PHONE_IDENTITY("/v*/cert/phone-identity/**", Set.of(HttpMethod.POST, HttpMethod.PUT), true),
    ;

    private final String path;
    private final Set<HttpMethod> methods;
    private final boolean shouldNotFilter;

    public static String[] getWhitelistByMethod(HttpMethod method) {
        return Arrays.stream(SecurityWhitelist.values())
            .filter(white -> white.getMethods().contains(method))
            .map(SecurityWhitelist::getPath)
            .toArray(String[]::new);
    }

    public static boolean isWhitelist(String contextPath, String path, HttpMethod method) {
        AntPathMatcher matcher = new AntPathMatcher();
        return Arrays.stream(SecurityWhitelist.values())
            .filter(white -> matcher.match(contextPath + white.getPath(), path))
            .anyMatch(white -> white.getMethods().contains(method));
    }

    public static boolean isShouldNotFilter(String contextPath, String path, HttpMethod method) {
        AntPathMatcher matcher = new AntPathMatcher();
        return Arrays.stream(SecurityWhitelist.values())
            .filter(white -> matcher.match(contextPath + white.getPath(), path))
            .filter(white -> white.getMethods().contains(method))
            .findFirst()
            .map(SecurityWhitelist::isShouldNotFilter)
            .orElse(false);
    }

}
