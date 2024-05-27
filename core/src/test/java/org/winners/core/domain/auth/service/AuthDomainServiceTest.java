package org.winners.core.domain.auth.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.domain.DomainServiceTest;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;

class AuthDomainServiceTest extends DomainServiceTest {

    private AuthDomainService authDomainService;

    private TokenProvider tokenProvider;

    @BeforeAll
    public void beforeAll() {
        this.tokenProvider = Mockito.mock(TokenProvider.class);
        this.authDomainService = new AuthDomainService(this.tokenProvider);
    }

    @Test
    @DisplayName("사용자 회원 인증토큰 생성")
    public void createClientUserAuthToken() {
        // given
        String accessToken = "test-access-token";
        String refreshToken = "test-refresh-token";
        given(tokenProvider.createAccessToken(anyLong(), anySet())).willReturn(accessToken);
        given(tokenProvider.createRefreshToken(anyLong(), anySet())).willReturn(refreshToken);

        // when
        AuthTokenDTO authToken = authDomainService.createClientUserAuthToken(1L);

        // then
        assertThat(authToken.getAccessToken()).isEqualTo(accessToken);
        assertThat(authToken.getRefreshToken()).isEqualTo(refreshToken);
    }

}