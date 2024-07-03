package com.winners.appApi.presentation.user.v1.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import com.winners.appApi.application.user.dto.SignInBusinessUserResultDTO;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SignInBusinessUserResponseDTO {

    private final long userId;
    private final String userName;
    private final String phoneNumber;
    private final Boolean isUpdatedPassword;
    private final String accessToken;
    private final String refreshToken;

    public static SignInBusinessUserResponseDTO create(SignInBusinessUserResultDTO result) {
        return SignInBusinessUserResponseDTO.builder()
            .userId(result.getUserId())
            .userName(result.getUserName())
            .phoneNumber(result.getPhoneNumber())
            .isUpdatedPassword(result.isUpdatedPassword())
            .accessToken(result.getAccessToken())
            .refreshToken(result.getRefreshToken())
            .build();
    }

}
