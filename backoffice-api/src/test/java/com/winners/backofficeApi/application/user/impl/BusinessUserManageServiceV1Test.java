package com.winners.backofficeApi.application.user.impl;

import com.winners.backofficeApi.config.ApplicationServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserMock;
import org.winners.core.domain.user.BusinessUserRepository;
import org.winners.core.domain.user.service.BusinessUserDomainService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class BusinessUserManageServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private BusinessUserManageServiceV1 businessUserManageServiceV1;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BusinessUserRepository businessUserRepository;

    @Mock
    private BusinessUserDomainService businessUserDomainService;

    @Test
    @DisplayName("사업자 회원 등록")
    void saveBusinessUser() {
        willDoNothing().given(businessUserDomainService).duplicateBusinessUserCheck(anyString());
        String encodedPassword = "encodedPassword486";
        given(passwordEncoder.encode(anyString())).willReturn(encodedPassword);
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        given(businessUserRepository.save(any(BusinessUser.class))).willReturn(businessUser);

        String userName = "홍길동";
        String phoneNumber = "01012341234";
        String password = "password486";
        businessUserManageServiceV1.saveBusinessUser(userName, phoneNumber, password);

        verify(businessUserDomainService).duplicateBusinessUserCheck(phoneNumber);
        verify(passwordEncoder).encode(password);
    }


}