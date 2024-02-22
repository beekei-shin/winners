package org.winners.app.application.field.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.winners.app.application.field.JobService;
import org.winners.app.application.field.dto.GetJobListSearchFieldDTO;
import org.winners.app.application.field.dto.JobDTO;

import java.util.List;
import java.util.Optional;

import static org.winners.core.domain.field.QJob.job;

@RequiredArgsConstructor
public class JobServiceV1 implements JobService {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<JobDTO> getJobList(GetJobListSearchFieldDTO searchField) {
        return queryFactory
            .select(Projections.constructor(JobDTO.class,
                job.id,
                job.name
            ))
            .from(job)
            .orderBy(job.sortOrder.asc())
            .where(Optional.ofNullable(searchField.getFieldId())
                .map(job.field.id::eq)
                .orElse(null))
            .fetch();
    }

}
