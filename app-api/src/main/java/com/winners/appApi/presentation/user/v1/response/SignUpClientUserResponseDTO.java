package com.winners.appApi.presentation.user.v1.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import com.winners.appApi.application.user.dto.SignUpClientUserResultDTO;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SignUpClientUserResponseDTO {

    private final long userId;
    private final String userName;
    private final String phoneNumber;
    private final String accessToken;
    private final String refreshToken;

    public static SignUpClientUserResponseDTO create(SignUpClientUserResultDTO result) {
        return SignUpClientUserResponseDTO.builder()
            .userId(result.getUserId())
            .userName(result.getUserName())
            .phoneNumber(result.getPhoneNumber())
            .accessToken(result.getAccessToken())
            .refreshToken(result.getRefreshToken())
            .build();
    }

}
