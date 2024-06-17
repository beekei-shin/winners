package org.winners.core.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.config.security.token.TokenRole;
import org.winners.core.domain.auth.AuthenticationHistory;
import org.winners.core.domain.auth.AuthenticationHistoryRepository;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.common.DeviceOs;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthDomainService {

    protected final static Set<TokenRole> CLIENT_USER_AUTH_TOKEN_ROLE = Set.of(TokenRole.CLIENT_USER);
    protected final static Set<TokenRole> ADMIN_USER_AUTH_TOKEN_ROLE = Set.of(TokenRole.ADMIN_USER);

    private final TokenProvider tokenProvider;
    private final AuthenticationHistoryRepository authenticationHistoryRepository;

    public AuthTokenDTO createClientUserAuthToken(long userId, DeviceOs deviceOs, String deviceToken) {
        String accessToken = tokenProvider.createAccessToken(userId, CLIENT_USER_AUTH_TOKEN_ROLE);
        LocalDateTime accessTokenExpireDate = tokenProvider.getAccessTokenExpiredDate(accessToken);
        String refreshToken = tokenProvider.createRefreshToken(userId, CLIENT_USER_AUTH_TOKEN_ROLE);
        LocalDateTime refreshTokenExpireDate = tokenProvider.getRefreshTokenExpiredDate(refreshToken);
        authenticationHistoryRepository.save(AuthenticationHistory.createClientUserAuthHistory(
            userId, deviceOs, deviceToken,
            accessToken, accessTokenExpireDate,
            refreshToken, refreshTokenExpireDate));
        return AuthTokenDTO.create(accessToken, refreshToken);
    }

    public AuthTokenDTO createAdminUserAuthToken(long userId) {
        String accessToken = tokenProvider.createAccessToken(userId, ADMIN_USER_AUTH_TOKEN_ROLE);
        LocalDateTime accessTokenExpireDate = tokenProvider.getAccessTokenExpiredDate(accessToken);
        String refreshToken = tokenProvider.createRefreshToken(userId, ADMIN_USER_AUTH_TOKEN_ROLE);
        LocalDateTime refreshTokenExpireDate = tokenProvider.getRefreshTokenExpiredDate(refreshToken);
        authenticationHistoryRepository.save(AuthenticationHistory.createAdminUserAuthHistory(
            userId,
            accessToken, accessTokenExpireDate,
            refreshToken, refreshTokenExpireDate));
        return AuthTokenDTO.create(accessToken, refreshToken);
    }

}
