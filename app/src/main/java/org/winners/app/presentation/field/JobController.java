package org.winners.app.presentation.field;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.winners.app.presentation.field.response.GetJobListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

public interface JobController {

    @Operation(summary = "직업 목록 조회")
    @GetMapping(name = "직업 목록 조회", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<GetJobListResponseDTO> getJobList(@RequestParam(required = false) Long fieldId);

}
