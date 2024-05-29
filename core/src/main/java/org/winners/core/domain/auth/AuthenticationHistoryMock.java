package org.winners.core.domain.auth;

import org.winners.core.domain.common.DeviceOs;

import java.time.LocalDateTime;

public class AuthenticationHistoryMock {

    public static AuthenticationHistory createHistory() {
        return AuthenticationHistory.create(1,
            DeviceOs.AOS, "deviceToken",
            "accessToken", LocalDateTime.now().plusDays(10),
            "refreshToken", LocalDateTime.now().plusDays(20));
    }

}
