package org.winners.app.presentation.user.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.presentation.user.ClientUserFieldController;
import org.winners.app.presentation.user.request.SaveClientUserFieldRequestDTO;
import org.winners.app.presentation.user.response.SaveClientUserFieldResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

@Tag(name = "202.v1. 사용자 회원 분야")
@RestController
@RequestMapping(value = "app/v1/user/client/field")
@RequiredArgsConstructor
public class ClientUserFieldControllerV1 implements ClientUserFieldController {

    @Override
    public ApiResponse<SaveClientUserFieldResponseDTO> saveClientUserField(SaveClientUserFieldRequestDTO request) {
        return null;
    }

}
