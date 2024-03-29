package org.winners.core.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.config.security.token.TokenRole;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthDomainService {

    private final TokenProvider tokenProvider;
    private final static Set<TokenRole> CLIENT_USER_AUTH_TOKEN_ROLE = Set.of(TokenRole.APP, TokenRole.CLIENT_USER);

    public AuthTokenDTO createClientUserAuthToken(long userId) {
        String accessToken = tokenProvider.createAccessToken(userId, CLIENT_USER_AUTH_TOKEN_ROLE);
        String refreshToken = tokenProvider.createAccessToken(userId, CLIENT_USER_AUTH_TOKEN_ROLE);
        return AuthTokenDTO.create(accessToken, refreshToken);
    }

}
