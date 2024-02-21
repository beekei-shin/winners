package org.winners.app.application.field.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobDTO {
    private final long jobId;
    private final String jobName;
}
