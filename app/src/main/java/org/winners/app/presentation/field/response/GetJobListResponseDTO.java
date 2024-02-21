package org.winners.app.presentation.field.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.winners.app.application.field.dto.JobDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetJobListResponseDTO {

    private final List<JobResponseDTO> jobList;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class JobResponseDTO {
        private long jobId;
        private String jobName;
    }

    public static GetJobListResponseDTO convert(List<JobDTO> jobList) {
        return new GetJobListResponseDTO(jobList.stream()
            .map(job -> JobResponseDTO.builder()
                .jobId(job.getJobId())
                .jobName(job.getJobName())
                .build())
            .collect(Collectors.toList()));
    }

}
