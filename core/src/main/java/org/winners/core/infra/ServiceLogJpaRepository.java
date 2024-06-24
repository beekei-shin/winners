package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.log.ServiceLog;
import org.winners.core.domain.log.ServiceLogRepository;

public interface ServiceLogJpaRepository extends JpaRepository<ServiceLog, Long>, ServiceLogRepository {
}
