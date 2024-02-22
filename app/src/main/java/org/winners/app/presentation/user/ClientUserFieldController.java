package org.winners.app.presentation.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.winners.app.presentation.user.request.SaveClientUserFieldRequestDTO;
import org.winners.app.presentation.user.response.SaveClientUserFieldResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

public interface ClientUserFieldController {

    @Operation(summary = "사용자 회원 분야 등록")
    @PostMapping(name = "사용자 회원 분야 등록", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<SaveClientUserFieldResponseDTO> saveClientUserField(@RequestBody @Valid SaveClientUserFieldRequestDTO request);

}
