package org.winners.app.presentation.user.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.winners.app.application.user.ClientUserSignService;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;
import org.winners.app.presentation.ControllerTest;
import org.winners.app.presentation.user.request.SignInClientUserRequestDTO;
import org.winners.app.presentation.user.request.SignUpClientUserRequestDTO;
import org.winners.app.presentation.user.response.SignInClientUserResponseDTO;
import org.winners.app.presentation.user.response.SignUpClientUserResponseDTO;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.common.DeviceOs;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserMock;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class ClientUserSignControllerV1Test extends ControllerTest {

    public ClientUserSignControllerV1Test() {
        super(ClientUserSignControllerV1.class);
    }

    @MockBean
    private ClientUserSignService clientUserSignService;

    @Test
    @DisplayName("사용자 회원 가입")
    void signUpClientUser() {
        ClientUser clientUser = ClientUserMock.createUser(1L);
        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        SignUpClientUserResultDTO signUpClientUserResult = SignUpClientUserResultDTO.success(clientUser, authToken);
        given(clientUserSignService.signUp(any(UUID.class), any(DeviceOs.class), anyString())).willReturn(signUpClientUserResult);

        SignUpClientUserRequestDTO request = new SignUpClientUserRequestDTO(UUID.randomUUID(), "AOS", "deviceToken");
        SignUpClientUserResponseDTO response = SignUpClientUserResponseDTO.create(signUpClientUserResult);
        this.postTest("up", request, response);
    }

    @Test
    @DisplayName("사용자 회원 로그인")
    void signInClientUser() {
        ClientUser clientUser = ClientUserMock.createUser(1L);
        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        SignInClientUserResultDTO signInClientUserResult = SignInClientUserResultDTO.success(clientUser, authToken);
        given(clientUserSignService.signIn(any(UUID.class), any(DeviceOs.class), anyString())).willReturn(signInClientUserResult);

        SignInClientUserRequestDTO request = new SignInClientUserRequestDTO(UUID.randomUUID(), "AOS", "deviceToken");
        SignInClientUserResponseDTO response = SignInClientUserResponseDTO.create(signInClientUserResult);
        this.postTest("in", request, response);
    }

}