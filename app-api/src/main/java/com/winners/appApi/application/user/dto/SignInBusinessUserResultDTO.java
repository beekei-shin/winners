package com.winners.appApi.application.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.user.BusinessUser;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SignInBusinessUserResultDTO {

    private final long userId;
    private final String userName;
    private final String phoneNumber;
    private final boolean isUpdatedPassword;
    private final String accessToken;
    private final String refreshToken;

    public static SignInBusinessUserResultDTO success(BusinessUser businessUser, boolean isUpdatedPassword, AuthTokenDTO authToken) {
        return SignInBusinessUserResultDTO.builder()
            .userId(businessUser.getId())
            .userName(businessUser.getName())
            .phoneNumber(businessUser.getPhoneNumber())
            .isUpdatedPassword(isUpdatedPassword)
            .accessToken(authToken.getAccessToken())
            .refreshToken(authToken.getRefreshToken())
            .build();
    }

}
