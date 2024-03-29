package org.winners.app.application.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SignInClientUserResultDTO {

    private final long userId;
    private final String accessToken;
    private final String refreshToken;

    public static SignInClientUserResultDTO success(long userId, String accessToken, String refreshToken) {
        return SignInClientUserResultDTO.builder()
            .userId(userId)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

}
