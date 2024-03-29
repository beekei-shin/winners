package org.winners.core.domain.auth.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class AuthTokenDTO {

    private final String accessToken;
    private final String refreshToken;

    public static AuthTokenDTO create(String accessToken, String refreshToken) {
        return AuthTokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

}
