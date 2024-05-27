package org.winners.app.presentation.user.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.application.user.ClientUserSignService;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;
import org.winners.app.presentation.AppController;
import org.winners.app.presentation.user.ClientUserSignController;
import org.winners.app.presentation.user.request.SignInClientUserRequestDTO;
import org.winners.app.presentation.user.request.SignUpClientUserRequestDTO;
import org.winners.app.presentation.user.response.SignInClientUserResponseDTO;
import org.winners.app.presentation.user.response.SignUpClientUserResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

@RestController
@Tag(name = "v1." + AppController.CLIENT_USER_SIGN_TAG_NAME)
@RequestMapping(value = "v1/" + AppController.CLIENT_USER_SIGN_PATH)
@RequiredArgsConstructor
public class ClientUserSignControllerV1 implements ClientUserSignController {

    private final ClientUserSignService clientUserSignServiceV1;

    @Override
    public ApiResponse<SignUpClientUserResponseDTO> signUpClientUser(SignUpClientUserRequestDTO request) {
        SignUpClientUserResultDTO result =  clientUserSignServiceV1.signUp(request.getAuthenticationKey());
        return ApiResponse.success(SignUpClientUserResponseDTO.create(result));
    }

    @Override
    public ApiResponse<SignInClientUserResponseDTO> signInClientUser(SignInClientUserRequestDTO request) {
        SignInClientUserResultDTO result = clientUserSignServiceV1.signIn(request.getCertificationKey());
        return ApiResponse.success(SignInClientUserResponseDTO.create(result));
    }

}
