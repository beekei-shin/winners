package org.winners.app.presentation.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.winners.app.presentation.user.request.SignInClientUserRequestDTO;
import org.winners.app.presentation.user.request.SignUpClientUserRequestDTO;
import org.winners.app.presentation.user.response.SignInClientUserResponseDTO;
import org.winners.app.presentation.user.response.SignUpClientUserResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

public interface ClientUserSignController {

    @Operation(summary = "사용자 회원 가입")
    @PostMapping(name = "사용자 회원 가입", value = "up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<SignUpClientUserResponseDTO> signUpClientUser(@RequestBody @Valid SignUpClientUserRequestDTO request);

    @Operation(summary = "사용자 회원 로그인")
    @PostMapping(name = "사용자 회원 로그인", value = "in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<SignInClientUserResponseDTO> signInClientUser(@RequestBody @Valid SignInClientUserRequestDTO request);

}
