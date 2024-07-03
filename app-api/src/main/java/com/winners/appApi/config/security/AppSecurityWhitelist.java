package com.winners.appApi.config.security;

import com.winners.appApi.presentation.AppController;
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
        new Whitelist("/v*/" + AppController.PHONE_IDENTITY_CERT_PATH + "/**", Set.of(HttpMethod.POST, HttpMethod.PUT)),
        new Whitelist("/v*/" + AppController.CLIENT_USER_SIGN_PATH + "/**", Set.of(HttpMethod.POST)),
        new Whitelist("/v*/" + AppController.BUSINESS_USER_SIGN_PATH + "/**", Set.of(HttpMethod.POST)),
        new Whitelist("/v*/" + AppController.SHOP_PATH + "/**", Set.of(HttpMethod.GET))
    );

}
