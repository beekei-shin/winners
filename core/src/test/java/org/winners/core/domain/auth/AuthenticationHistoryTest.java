package org.winners.core.domain.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.winners.core.domain.common.DeviceOs;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationHistoryTest {

    @Test
    @DisplayName("사용자 회원 인증내역 생성")
    void createClientUserAuthHistory() {
        long userId = 10;
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        String accessToken = "accessToken";
        LocalDateTime accessTokenExpireDatetime = LocalDateTime.now().plusDays(10);
        String refreshToken = "refreshToken";
        LocalDateTime refreshTokenExpireDatetime = LocalDateTime.now().plusDays(20);

        AuthenticationHistory authenticationHistory = AuthenticationHistory.createClientUserAuthHistory(
            userId, deviceOs, deviceToken,
            accessToken, accessTokenExpireDatetime,
            refreshToken, refreshTokenExpireDatetime);

        assertThat(authenticationHistory.getUserId()).isEqualTo(userId);
        assertThat(authenticationHistory.getDeviceOs()).isEqualTo(deviceOs);
        assertThat(authenticationHistory.getDeviceToken()).isEqualTo(deviceToken);
        assertThat(authenticationHistory.getAccessToken()).isEqualTo(accessToken);
        assertThat(authenticationHistory.getAccessTokenExpireDatetime()).isEqualTo(accessTokenExpireDatetime);
        assertThat(authenticationHistory.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(authenticationHistory.getRefreshTokenExpireDatetime()).isEqualTo(refreshTokenExpireDatetime);
    }

    @Test
    @DisplayName("관리자 회원 인증내역 생성")
    void createAdminUserAuthHistory() {
        long userId = 10;
        String accessToken = "accessToken";
        LocalDateTime accessTokenExpireDatetime = LocalDateTime.now().plusDays(10);
        String refreshToken = "refreshToken";
        LocalDateTime refreshTokenExpireDatetime = LocalDateTime.now().plusDays(20);

        AuthenticationHistory authenticationHistory = AuthenticationHistory.createAdminUserAuthHistory(
            userId,
            accessToken, accessTokenExpireDatetime,
            refreshToken, refreshTokenExpireDatetime);

        assertThat(authenticationHistory.getUserId()).isEqualTo(userId);
        assertThat(authenticationHistory.getDeviceOs()).isNull();
        assertThat(authenticationHistory.getDeviceToken()).isNull();
        assertThat(authenticationHistory.getAccessToken()).isEqualTo(accessToken);
        assertThat(authenticationHistory.getAccessTokenExpireDatetime()).isEqualTo(accessTokenExpireDatetime);
        assertThat(authenticationHistory.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(authenticationHistory.getRefreshTokenExpireDatetime()).isEqualTo(refreshTokenExpireDatetime);
    }

}