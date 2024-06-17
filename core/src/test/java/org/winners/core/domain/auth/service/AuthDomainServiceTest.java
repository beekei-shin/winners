package org.winners.core.domain.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.domain.auth.AuthenticationHistory;
import org.winners.core.domain.auth.AuthenticationHistoryMock;
import org.winners.core.domain.auth.AuthenticationHistoryRepository;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.common.DeviceOs;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class AuthDomainServiceTest extends DomainServiceTest {

    private AuthDomainService authDomainService;
    private TokenProvider tokenProvider;
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @BeforeEach
    public void BeforeEach() {
        this.tokenProvider = Mockito.mock(TokenProvider.class);
        this.authenticationHistoryRepository = Mockito.mock(AuthenticationHistoryRepository.class);
        this.authDomainService = new AuthDomainService(this.tokenProvider, this.authenticationHistoryRepository);
    }

    @Test
    @DisplayName("사용자 회원 인증토큰 생성")
    public void createClientUserAuthToken() {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        given(tokenProvider.createAccessToken(anyLong(), anySet())).willReturn(accessToken);
        given(tokenProvider.getAccessTokenExpiredDate(anyString())).willReturn(LocalDateTime.of(2024, 1, 1, 12, 0, 0));
        given(tokenProvider.createRefreshToken(anyLong(), anySet())).willReturn(refreshToken);
        given(tokenProvider.getRefreshTokenExpiredDate(anyString())).willReturn(LocalDateTime.of(2024, 1, 1, 12, 0, 0));

        AuthenticationHistory authenticationHistory = AuthenticationHistoryMock.createHistory();
        given(authenticationHistoryRepository.save(any(AuthenticationHistory.class))).willReturn(authenticationHistory);

        // when
        long userId = 1L;
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        AuthTokenDTO authToken = authDomainService.createClientUserAuthToken(userId, deviceOs, deviceToken);

        // then
        assertThat(authToken.getAccessToken()).isEqualTo(accessToken);
        assertThat(authToken.getRefreshToken()).isEqualTo(refreshToken);
        verify(tokenProvider).createAccessToken(userId, AuthDomainService.CLIENT_USER_AUTH_TOKEN_ROLE);
        verify(tokenProvider).getAccessTokenExpiredDate(accessToken);
        verify(tokenProvider).createRefreshToken(userId, AuthDomainService.CLIENT_USER_AUTH_TOKEN_ROLE);
        verify(tokenProvider).getRefreshTokenExpiredDate(refreshToken);
    }

}