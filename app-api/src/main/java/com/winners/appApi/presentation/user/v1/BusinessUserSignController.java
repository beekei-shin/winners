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
import com.winners.appApi.application.user.BusinessUserSignService;
import com.winners.appApi.application.user.dto.SignInBusinessUserResultDTO;
import com.winners.appApi.presentation.user.v1.request.SignInBusinessUserRequestDTO;
import com.winners.appApi.presentation.user.v1.response.SignInBusinessUserResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.version.ApiVersion;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = AppController.BUSINESS_USER_SIGN_TAG_NAME)
@RequestMapping(path = AppController.BUSINESS_USER_SIGN_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class BusinessUserSignController {

    private final BusinessUserSignService businessUserSignServiceV1;

    @Operation(summary = "사업자 회원 로그인")
    @PostMapping(name = "사업자 회원 로그인", value = "in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<SignInBusinessUserResponseDTO> signInBusinessUser(@RequestBody @Valid SignInBusinessUserRequestDTO request) {
        SignInBusinessUserResultDTO result = businessUserSignServiceV1.signIn(
            request.getPhoneNumber(),
            request.getPassword());
        return ApiResponse.success(SignInBusinessUserResponseDTO.create(result));
    }

}
