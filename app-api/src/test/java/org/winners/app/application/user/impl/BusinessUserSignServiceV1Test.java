package org.winners.app.application.user.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.winners.app.application.user.dto.SignInBusinessUserResultDTO;
import org.winners.app.config.ApplicationServiceTest;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.InvalidDataException;
import org.winners.core.domain.auth.service.AuthDomainService;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.log.service.ServiceLogDomainService;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserMock;
import org.winners.core.domain.user.service.BusinessUserDomainService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BusinessUserSignServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private BusinessUserSignServiceV1 businessUserSignServiceV1;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BusinessUserDomainService businessUserDomainService;

    @Mock
    private ServiceLogDomainService serviceLogDomainService;

    @Mock
    private AuthDomainService authDomainService;

    @Test
    @DisplayName("사업자 회원 로그인")
    void signIn() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        given(businessUserDomainService.getBusinessUserByPhoneNumber(anyString())).willReturn(businessUser);
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        boolean hasUpdatedPassword = true;
        given(serviceLogDomainService.hasUpdatedPasswordLog(anyLong())).willReturn(hasUpdatedPassword);

        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        given(authDomainService.createBusinessUserAuthToken(anyLong())).willReturn(authToken);

        String phoneNumber = "01012341234";
        String password = "password486";
        SignInBusinessUserResultDTO result = businessUserSignServiceV1.signIn(phoneNumber, password);

        assertThat(result.getUserId()).isEqualTo(businessUser.getId());
        assertThat(result.getUserName()).isEqualTo(businessUser.getName());
        assertThat(result.getPhoneNumber()).isEqualTo(businessUser.getPhoneNumber());
        assertThat(result.isUpdatedPassword()).isEqualTo(hasUpdatedPassword);
        assertThat(result.getAccessToken()).isEqualTo(authToken.getAccessToken());
        assertThat(result.getRefreshToken()).isEqualTo(authToken.getRefreshToken());
    }

    @Test
    @DisplayName("사업자 회원 로그인 - 비밀번호 불일치")
    void signIn_invalidPassword() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        given(businessUserDomainService.getBusinessUserByPhoneNumber(anyString())).willReturn(businessUser);
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        String phoneNumber = "01012341234";
        String password = "password486";
        Throwable exception = assertThrows(InvalidDataException.class,
            () -> businessUserSignServiceV1.signIn(phoneNumber, password));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.INVALID_PASSWORD.getMessage());
        verify(businessUserDomainService).getBusinessUserByPhoneNumber(phoneNumber);
        verify(passwordEncoder).matches(password, businessUser.getPassword());
    }

}