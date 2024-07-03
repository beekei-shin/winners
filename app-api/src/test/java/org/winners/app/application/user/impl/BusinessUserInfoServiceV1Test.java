package org.winners.app.application.user.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.winners.app.config.ApplicationServiceTest;
import org.winners.core.domain.log.service.ServiceLogDomainService;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserMock;
import org.winners.core.domain.user.service.BusinessUserDomainService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BusinessUserInfoServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private BusinessUserInfoServiceV1 businessUserInfoServiceV1;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BusinessUserDomainService businessUserDomainService;

    @Mock
    private ServiceLogDomainService serviceLogDomainService;

    @Test
    @DisplayName("비밀번호 변경 여부 조회")
    void isUpdatedPassword() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        given(businessUserDomainService.getBusinessUser(anyLong())).willReturn(businessUser);

        long userId = 1;

        given(serviceLogDomainService.hasUpdatedPasswordLog(anyLong())).willReturn(true);
        boolean returnIsUpdatedPassword = businessUserInfoServiceV1.isUpdatedPassword(userId);
        assertThat(returnIsUpdatedPassword).isEqualTo(true);

        given(serviceLogDomainService.hasUpdatedPasswordLog(anyLong())).willReturn(false);
        boolean returnIsUpdatedPassword2 = businessUserInfoServiceV1.isUpdatedPassword(userId);
        assertThat(returnIsUpdatedPassword2).isEqualTo(false);

        verify(businessUserDomainService, times(2)).getBusinessUser(userId);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void updatePassword() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        given(businessUserDomainService.getBusinessUser(anyLong())).willReturn(businessUser);
        String encodedPassword = "encodedNewPassword";
        given(passwordEncoder.encode(anyString())).willReturn(encodedPassword);
        willDoNothing().given(serviceLogDomainService).saveUpdatePasswordLog(anyLong());

        long userId = 1;
        String password = "newPassword";
        businessUserInfoServiceV1.updatePassword(userId, password);

        assertThat(businessUser.getPassword()).isEqualTo(encodedPassword);
        verify(businessUserDomainService).getBusinessUser(userId);
        verify(passwordEncoder).encode(password);
    }

}