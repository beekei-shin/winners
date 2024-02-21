package org.winners.app.presentation.field.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.application.field.JobService;
import org.winners.app.application.field.dto.GetJobListSearchFieldDTO;
import org.winners.app.application.field.dto.JobDTO;
import org.winners.app.presentation.field.JobController;
import org.winners.app.presentation.field.response.GetJobListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobControllerV1 implements JobController {

    @Qualifier("JobServiceV1")
    private final JobService jobService;

    @Override
    public ApiResponse<GetJobListResponseDTO> getJobList(@RequestParam(required = false) Long fieldId) {
        final GetJobListSearchFieldDTO searchField = GetJobListSearchFieldDTO.builder()
            .fieldId(fieldId)
            .build();
        final List<JobDTO> jobList = jobService.getJobList(searchField);
        return ApiResponse.success(GetJobListResponseDTO.convert(jobList));
    }

}
