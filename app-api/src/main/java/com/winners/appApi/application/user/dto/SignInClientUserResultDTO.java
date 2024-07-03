package com.winners.appApi.application.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.user.ClientUser;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SignInClientUserResultDTO {

    private final long userId;
    private final String userName;
    private final String phoneNumber;
    private final String accessToken;
    private final String refreshToken;

    public static SignInClientUserResultDTO success(ClientUser clientUser, AuthTokenDTO authToken) {
        return SignInClientUserResultDTO.builder()
            .userId(clientUser.getId())
            .userName(clientUser.getName())
            .phoneNumber(clientUser.getPhoneNumber())
            .accessToken(authToken.getAccessToken())
            .refreshToken(authToken.getRefreshToken())
            .build();
    }

}
