package com.winners.appApi.presentation.user.v1;

import com.winners.appApi.presentation.AppController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.winners.appApi.application.user.ClientUserSignService;
import com.winners.appApi.application.user.dto.SignInClientUserResultDTO;
import com.winners.appApi.application.user.dto.SignUpClientUserResultDTO;
import com.winners.appApi.presentation.user.v1.request.SignInClientUserRequestDTO;
import com.winners.appApi.presentation.user.v1.request.SignUpClientUserRequestDTO;
import com.winners.appApi.presentation.user.v1.response.SignInClientUserResponseDTO;
import com.winners.appApi.presentation.user.v1.response.SignUpClientUserResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.version.ApiVersion;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = AppController.CLIENT_USER_SIGN_TAG_NAME)
@RequestMapping(path = AppController.CLIENT_USER_SIGN_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientUserSignController {

    private final ClientUserSignService clientUserSignServiceV1;

    @Operation(summary = "사용자 회원 가입")
    @PostMapping(name = "사용자 회원 가입", value = "up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<SignUpClientUserResponseDTO> signUpClientUser(@RequestBody @Valid SignUpClientUserRequestDTO request) {
        SignUpClientUserResultDTO result =  clientUserSignServiceV1.signUp(
            request.getCertificationKey(),
            request.getDeviceOs(),
            request.getDeviceToken());
        return ApiResponse.success(SignUpClientUserResponseDTO.create(result));
    }

    @Operation(summary = "사용자 회원 로그인")
    @PostMapping(name = "사용자 회원 로그인", value = "in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<SignInClientUserResponseDTO> signInClientUser(@RequestBody @Valid SignInClientUserRequestDTO request) {
        SignInClientUserResultDTO result = clientUserSignServiceV1.signIn(
            request.getCertificationKey(),
            request.getDeviceOs(),
            request.getDeviceToken());
        return ApiResponse.success(SignInClientUserResponseDTO.create(result));
    }

}
