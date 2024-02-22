package org.winners.core.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum SecurityWhitelist {

    COMMON(Map.ofEntries(
        Map.entry("/health", Set.of(HttpMethod.GET)),
        Map.entry("/common/**", Set.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE)),
        Map.entry("/app/auth/**", Set.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE)),
        Map.entry("/web/auth/**", Set.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE)),
        Map.entry("/toss/auth/**", Set.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE)),
        Map.entry("/admin/auth/**", Set.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE)),
        Map.entry("/test/**", Set.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE)), // 제거 필요
        Map.entry("/admin/**", Set.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE)) // 제거 필요
    )),
    CERT(Map.ofEntries(
        Map.entry("/app/cert/phone/**", Set.of(HttpMethod.GET, HttpMethod.POST)),
        Map.entry("/app/cert/pin", Set.of(HttpMethod.POST, HttpMethod.PUT)),
        Map.entry("/app/cert/biometric", Set.of(HttpMethod.POST, HttpMethod.PUT)),
        Map.entry("/app/cert/account-number/otp", Set.of(HttpMethod.POST)),
        Map.entry("/app/cert/account-number/otp/confirm", Set.of(HttpMethod.POST)),

        Map.entry("/web/cert/phone/**", Set.of(HttpMethod.GET, HttpMethod.POST)),
        Map.entry("/web/cert/pin", Set.of(HttpMethod.POST, HttpMethod.PUT)),

        Map.entry("/toss/cert/identity/**", Set.of(HttpMethod.POST, HttpMethod.GET)),
        Map.entry("/toss/cert/pin", Set.of(HttpMethod.PUT)),

        Map.entry("/admin/cert/login", Set.of(HttpMethod.POST)),
        Map.entry("/admin/user/admin", Set.of(HttpMethod.POST)) // 제거 필요

    )),
    USER(Map.ofEntries(
        Map.entry("/app/user/client", Set.of(HttpMethod.POST)),
        Map.entry("/app/user/client/status", Set.of(HttpMethod.GET)),
        Map.entry("/app/user/client/leave/restore", Set.of(HttpMethod.POST)),

        Map.entry("/web/user/client", Set.of(HttpMethod.POST)),
        Map.entry("/web/user/client/status", Set.of(HttpMethod.GET)),

        Map.entry("/toss/user/client/status", Set.of(HttpMethod.GET)),
        Map.entry("/toss/user/client", Set.of(HttpMethod.POST))
    )),
    TERMS(Map.ofEntries(
        Map.entry("/app/v3/terms-group/**", Set.of(HttpMethod.GET)),
        Map.entry("/app/v3/terms-detail/**", Set.of(HttpMethod.GET)),
        Map.entry("/toss/terms-group/**", Set.of(HttpMethod.GET)),
        Map.entry("/toss/terms-detail/**", Set.of(HttpMethod.GET))
    )),
    PRODUCT(Map.ofEntries(
        Map.entry("/app/v3/product-detail/**", Set.of(HttpMethod.GET)),
        Map.entry("/toss/product-detail/**", Set.of(HttpMethod.GET))
    )),
    CATTLE(Map.ofEntries(
        Map.entry("/app/v3/profit-cattle-list/**", Set.of(HttpMethod.GET))
    )),
    ;

    private final Map<String, Set<HttpMethod>> pathMap;

    public static String[] getWhitelistPath(HttpMethod httpMethod) {
        return Arrays.stream(SecurityWhitelist.values())
            .map(SecurityWhitelist::getPathMap)
            .map(pathMap -> pathMap.keySet().stream()
                .filter(path -> pathMap.get(path).contains(httpMethod))
                .collect(Collectors.toList()))
            .reduce(new ArrayList<>(), (a, b) -> { a.addAll(b); return a; })
            .toArray(new String[0]);
    }


}
