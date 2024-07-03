package org.winners.app.presentation.user.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.winners.app.application.user.ClientUserSignService;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;
import org.winners.app.config.ControllerTest;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.common.DeviceOs;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserMock;

import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ClientUserSignControllerTest extends ControllerTest {

    public ClientUserSignControllerTest() {
        super(ClientUserSignController.class);
    }

    @MockBean
    private ClientUserSignService clientUserSignService;

    @Test
    @DisplayName("사용자 회원 가입")
    void signUpClientUser() {
        ClientUser clientUser = ClientUserMock.createUser(1L);
        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        given(clientUserSignService.signUp(any(UUID.class), any(DeviceOs.class), anyString()))
            .willReturn(SignUpClientUserResultDTO.success(clientUser, authToken));

        UUID certificationKey = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        mvcTest("up", HttpMethod.POST)
            .requestBody(
                Map.entry("certificationKey", certificationKey.toString()),
                Map.entry("deviceOs", deviceOs.toString()),
                Map.entry("deviceToken", deviceToken))
            .responseBody(
                Map.entry("userId", clientUser.getId()),
                Map.entry("userName", clientUser.getName()),
                Map.entry("phoneNumber", clientUser.getPhoneNumber()),
                Map.entry("accessToken", authToken.getAccessToken()),
                Map.entry("refreshToken", authToken.getRefreshToken()))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(clientUserSignService).signUp(certificationKey, deviceOs, deviceToken);
    }

    @Test
    @DisplayName("사용자 회원 로그인")
    void signInClientUser() {
        ClientUser clientUser = ClientUserMock.createUser(1L);
        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        given(clientUserSignService.signIn(any(UUID.class), any(DeviceOs.class), anyString()))
            .willReturn(SignInClientUserResultDTO.success(clientUser, authToken));

        UUID certificationKey = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        mvcTest("in", HttpMethod.POST)
            .requestBody(
                Map.entry("certificationKey", certificationKey.toString()),
                Map.entry("deviceOs", deviceOs.toString()),
                Map.entry("deviceToken", deviceToken))
            .responseBody(
                Map.entry("userId", clientUser.getId()),
                Map.entry("userName", clientUser.getName()),
                Map.entry("phoneNumber", clientUser.getPhoneNumber()),
                Map.entry("accessToken", authToken.getAccessToken()),
                Map.entry("refreshToken", authToken.getRefreshToken()))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(clientUserSignService).signIn(certificationKey, deviceOs, deviceToken);
    }

}