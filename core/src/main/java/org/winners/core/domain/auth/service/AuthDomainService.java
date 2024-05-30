package org.winners.core.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.token.TokenProvider;
import org.winners.core.config.token.TokenRole;
import org.winners.core.domain.auth.AuthenticationHistory;
import org.winners.core.domain.auth.AuthenticationHistoryRepository;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.common.DeviceOs;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthDomainService {

    protected final static Set<TokenRole> CLIENT_USER_AUTH_TOKEN_ROLE = Set.of(TokenRole.APP, TokenRole.CLIENT_USER);

    private final TokenProvider tokenProvider;
    private final AuthenticationHistoryRepository authenticationHistoryRepository;

    public AuthTokenDTO createClientUserAuthToken(long userId, DeviceOs deviceOs, String deviceToken) {
        String accessToken = tokenProvider.createAccessToken(userId, CLIENT_USER_AUTH_TOKEN_ROLE);
        LocalDateTime accessTokenExpireDate = tokenProvider.getAccessTokenExpiredDate(accessToken);
        String refreshToken = tokenProvider.createRefreshToken(userId, CLIENT_USER_AUTH_TOKEN_ROLE);
        LocalDateTime refreshTokenExpireDate = tokenProvider.getRefreshTokenExpiredDate(refreshToken);
        authenticationHistoryRepository.save(AuthenticationHistory.createHistory(
            userId, deviceOs, deviceToken,
            accessToken, accessTokenExpireDate,
            refreshToken, refreshTokenExpireDate));
        return AuthTokenDTO.create(accessToken, refreshToken);
    }

}
