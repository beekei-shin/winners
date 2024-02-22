package org.winners.app.presentation.field;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.presentation.field.response.GetFieldListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

public interface FieldController {

    @Operation(summary = "분야 목록 조회")
    @GetMapping(name = "분야 목록 조회", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<GetFieldListResponseDTO> getFieldList();

}
