package org.winners.app.presentation.user.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.application.user.ClientUserSignService;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;
import org.winners.app.presentation.user.ClientUserSignController;
import org.winners.app.presentation.user.request.SignInClientUserRequestDTO;
import org.winners.app.presentation.user.request.SignUpClientUserRequestDTO;
import org.winners.app.presentation.user.response.SignInClientUserResponseDTO;
import org.winners.app.presentation.user.response.SignUpClientUserResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

@Tag(name = "201.v1. 사용자 회원 서명")
@RestController
@RequestMapping(value = "v1/user/client/sign")
@RequiredArgsConstructor
public class ClientUserSignControllerV1 implements ClientUserSignController {

    @Qualifier("ClientUserSignServiceV1")
    private final ClientUserSignService clientUserSignService;

    @Override
    public ApiResponse<?> signUpClientUser(SignUpClientUserRequestDTO request) {
        clientUserSignService.signUp(request.getAuthenticationKey());
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<?> signInClientUser(SignInClientUserRequestDTO request) {
        clientUserSignService.signIn(request.getCertificationKey());
        return ApiResponse.success();
    }

}
