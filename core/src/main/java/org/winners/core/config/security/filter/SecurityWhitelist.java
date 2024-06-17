package org.winners.core.config.security.filter;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Component
public interface SecurityWhitelist {

    List<Whitelist> getWhitelist();

    default String[] getWhitelistByMethod(HttpMethod method) {
        return this.getWhitelist().stream()
            .filter(white -> white.getMethods().contains(method))
            .map(Whitelist::getPath)
            .toArray(String[]::new);
    }

    default boolean isWhitelist(String contextPath, String path, HttpMethod method) {
        AntPathMatcher matcher = new AntPathMatcher();
        return this.getWhitelist().stream()
            .filter(white -> matcher.match(contextPath + white.getPath(), path))
            .anyMatch(white -> white.getMethods().contains(method));
    }
    default boolean isShouldNotFilter(String contextPath, String path, HttpMethod method) {
        AntPathMatcher matcher = new AntPathMatcher();
        return this.getWhitelist().stream()
            .filter(white -> matcher.match(contextPath + white.getPath(), path))
            .filter(white -> white.getMethods().contains(method))
            .findFirst()
            .map(Whitelist::isShouldNotFilter)
            .orElse(false);
    };

}
