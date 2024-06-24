package org.winners.core.domain.log;

public interface ServiceLogRepository {
    long countByUserIdAndType(long userId, ServiceLogType type);
    ServiceLog save(ServiceLog serviceLog);
}
