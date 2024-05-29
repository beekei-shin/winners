package org.winners.core.domain.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.winners.core.domain.cert.CertificationType;
import org.winners.core.domain.common.DeviceOs;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationHistoryTest {

    @Test
    @DisplayName("인증내역 생성")
    void create() {
        long userId = 10;
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        String accessToken = "accessToken";
        LocalDateTime accessTokenExpireDatetime = LocalDateTime.now().plusDays(10);
        String refreshToken = "refreshToken";
        LocalDateTime refreshTokenExpireDatetime = LocalDateTime.now().plusDays(20);

        AuthenticationHistory authenticationHistory = AuthenticationHistory.create(userId, deviceOs, deviceToken,
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

}