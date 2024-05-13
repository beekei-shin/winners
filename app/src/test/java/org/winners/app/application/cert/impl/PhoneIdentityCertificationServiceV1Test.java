package org.winners.app.application.cert.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.winners.app.application.ServiceTest;
import org.winners.core.config.exception.AlreadyProcessedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.base.Gender;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.CertificationKeyMock;
import org.winners.core.domain.cert.CertificationType;
import org.winners.core.domain.cert.MobileCarrier;
import org.winners.core.domain.cert.service.CertificationKeyDomainService;
import org.winners.core.domain.cert.service.PhoneIdentityCertificationDomainService;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.winners.core.config.exception.ExceptionMessageType.ALREADY_CERTIFIED_AUTHENTICATION_KEY;

class PhoneIdentityCertificationServiceV1Test extends ServiceTest {

    @Autowired
    @InjectMocks
    private PhoneIdentityCertificationServiceV1 phoneIdentityCertificationServiceV1;

    @MockBean
    private CertificationKeyDomainService certificationKeyDomainService;

    @MockBean
    private PhoneIdentityCertificationDomainService phoneIdentityCertificationDomainService;

    @Test
    @DisplayName("휴대폰 본인인증 인증 OTP 번호 전송")
    public void sendPhoneIdentityOtpNumber() {
        // given
        CertificationKey issuedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        given(certificationKeyDomainService.createCertificationKey(any(CertificationType.class), anyInt()))
            .willReturn(issuedCertificationKey);
        given(phoneIdentityCertificationDomainService.sendOtpNumber(any(CertificationKey.class), any(SendOtpNumberParameterDTO.class)))
            .willReturn(issuedCertificationKey.getId());

        // when
        UUID certificationKeyId = phoneIdentityCertificationServiceV1.sendPhoneIdentityOtpNumber(SendOtpNumberParameterDTO.builder()
            .name("홍길동")
            .birthday(LocalDate.of(1993, 10, 20))
            .gender(Gender.MALE)
            .mobileCarrier(MobileCarrier.LG)
            .phoneNumber("01011112222")
            .build());

        // then
        assertThat(certificationKeyId).isEqualTo(issuedCertificationKey.getId());
    }

    @Test
    @DisplayName("휴대폰 본인인증 인증 OTP 번호 인증")
    public void certifyPhoneIdentityOtpNumber() {
        // given
        CertificationKey issuedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(issuedCertificationKey);
        doNothing().when(certificationKeyDomainService).possibleCertifyCheck(any(CertificationKey.class));
        doNothing().when(phoneIdentityCertificationDomainService).certifyOtpNumber(any(CertificationKey.class), anyString(), anyString());

        // when
        UUID certificationKey = UUID.randomUUID();
        String phoneNumber = "01011112222";
        String pinNumber = "000000";
        phoneIdentityCertificationServiceV1.certifyPhoneIdentityOtpNumber(certificationKey, phoneNumber, pinNumber);

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKey);
        verify(certificationKeyDomainService).possibleCertifyCheck(issuedCertificationKey);
        verify(phoneIdentityCertificationDomainService).certifyOtpNumber(issuedCertificationKey, phoneNumber, pinNumber);
    }

    @Test
    @DisplayName("휴대폰 본인인증 인증 OTP 번호 인증 - 존재하지 않는 인증키")
    public void certifyPhoneIdentityOtpNumber_notExistAuthenticationKey() {
        // given
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class)))
            .willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_AUTHENTICATION_KEY));

        // when
        UUID certificationKey = UUID.randomUUID();
        String phoneNumber = "01011112222";
        String pinNumber = "000000";
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> phoneIdentityCertificationServiceV1.certifyPhoneIdentityOtpNumber(certificationKey, phoneNumber, pinNumber));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_AUTHENTICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKey);
    }

    @Test
    @DisplayName("휴대폰 본인인증 인증 OTP 번호 인증 - 인증이 불가능한 인증키")
    public void certifyPhoneIdentityOtpNumber_notPossibleCertifyAuthenticationKey() {
        // given
        CertificationKey issuedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(issuedCertificationKey);
        doThrow(new AlreadyProcessedDataException(ALREADY_CERTIFIED_AUTHENTICATION_KEY))
            .when(certificationKeyDomainService).possibleCertifyCheck(any(CertificationKey.class));

        // when
        UUID certificationKey = UUID.randomUUID();
        String phoneNumber = "01011112222";
        String pinNumber = "000000";
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> phoneIdentityCertificationServiceV1.certifyPhoneIdentityOtpNumber(certificationKey, phoneNumber, pinNumber));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_CERTIFIED_AUTHENTICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKey);
        verify(certificationKeyDomainService).possibleCertifyCheck(issuedCertificationKey);
    }

}