package org.winners.app.presentation.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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

@RestController
@RequiredArgsConstructor
public class ClientUserSignControllerV1 implements ClientUserSignController {

    @Qualifier("ClientUserSignServiceV1")
    private final ClientUserSignService clientUserSignService;

    @Override
    public ApiResponse<SignUpClientUserResponseDTO> signUpClientUser(SignUpClientUserRequestDTO request) {
        final SignUpClientUserResultDTO result = clientUserSignService.signUpClientUser(request.getAuthenticationKey());
        return ApiResponse.success(new SignUpClientUserResponseDTO(
            result.isSuccessSignUp(),
            result.isDuplicatedPhoneNumber(),
            result.isDuplicatedCi()));
    }

    @Override
    public ApiResponse<SignInClientUserResponseDTO> signInClientUser(SignInClientUserRequestDTO request) {
        final SignInClientUserResultDTO result = clientUserSignService.signInClientUser(request.getAuthenticationKey());
        return ApiResponse.success(new SignInClientUserResponseDTO(
            result.isSuccessSignIn(),
            result.isNotExistUser(),
            result.isBlockUser()));
    }

}
