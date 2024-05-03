package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.field.Job;
import org.winners.core.domain.field.JobRepository;

public interface JobJpaRepository extends JpaRepository<Job, Long>, JobRepository {
}
