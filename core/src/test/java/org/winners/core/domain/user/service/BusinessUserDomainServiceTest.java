package org.winners.core.domain.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.domain.auth.AuthenticationHistoryRepository;
import org.winners.core.domain.auth.service.AuthDomainService;
import org.winners.core.domain.user.BusinessUserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BusinessUserDomainServiceTest extends DomainServiceTest {

    private BusinessUserDomainService businessUserDomainService;
    private BusinessUserRepository businessUserRepository;

    @BeforeEach
    public void BeforeEach() {
        this.businessUserRepository = Mockito.mock(BusinessUserRepository.class);
        this.businessUserDomainService = new BusinessUserDomainService(this.businessUserRepository);
    }

    @Test
    @DisplayName("사업자 회원 중복 확인")
    void duplicateBusinessUserCheck() {
        given(businessUserRepository.countByPhoneNumber(anyString())).willReturn(0L);

        String phoneNumber = "01012341234";
        businessUserDomainService.duplicateBusinessUserCheck(phoneNumber);

        verify(businessUserRepository).countByPhoneNumber(phoneNumber);
    }

    @Test
    @DisplayName("사업자 회원 중복 확인 - 중복된 사업자 회원")
    void duplicateBusinessUserCheck_duplicatedBusinessUser() {
        given(businessUserRepository.countByPhoneNumber(anyString())).willReturn(1L);

        String phoneNumber = "01012341234";

        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> businessUserDomainService.duplicateBusinessUserCheck(phoneNumber));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_PHONE_NUMBER.getMessage());
        verify(businessUserRepository).countByPhoneNumber(phoneNumber);
    }

}