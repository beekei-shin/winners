package org.winners.core.domain.log.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.domain.log.ServiceLogRepository;
import org.winners.core.domain.log.ServiceLogType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ServiceLogDomainServiceTest extends DomainServiceTest {

    private ServiceLogDomainService serviceLogDomainService;
    private ServiceLogRepository serviceLogRepository;

    @BeforeEach
    public void BeforeEach() {
        this.serviceLogRepository = Mockito.mock(ServiceLogRepository.class);
        this.serviceLogDomainService = new ServiceLogDomainService(this.serviceLogRepository);
    }

    @Test
    @DisplayName("비밀번호 변경 로그 횟수 조회")
    void hasCountUpdatedPasswordLog() {
        long userId = 1;

        given(serviceLogRepository.countByUserIdAndType(anyLong(), any(ServiceLogType.class))).willReturn(1L);
        boolean hasUpdatedPasswordLog1 = serviceLogDomainService.hasUpdatedPasswordLog(userId);
        assertThat(hasUpdatedPasswordLog1).isTrue();

        given(serviceLogRepository.countByUserIdAndType(anyLong(), any(ServiceLogType.class))).willReturn(0L);
        boolean hasUpdatedPasswordLog2 = serviceLogDomainService.hasUpdatedPasswordLog(userId);
        assertThat(hasUpdatedPasswordLog2).isFalse();

        verify(serviceLogRepository, times(2)).countByUserIdAndType(userId, ServiceLogType.UPDATE_PASSWORD);
    }

}