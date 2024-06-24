package org.winners.core.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.domain.log.ServiceLog;
import org.winners.core.domain.log.ServiceLogRepository;
import org.winners.core.domain.log.ServiceLogType;

@Service
@RequiredArgsConstructor
public class ServiceLogDomainService {

    private final ServiceLogRepository serviceLogRepository;

    public boolean hasUpdatedPasswordLog(long userId) {
        return serviceLogRepository.countByUserIdAndType(userId, ServiceLogType.UPDATE_PASSWORD) > 0;
    }

    public void saveUpdatePasswordLog(long userId) {
        serviceLogRepository.save(ServiceLog.createUpdatePasswordLog(userId));
    }

}
