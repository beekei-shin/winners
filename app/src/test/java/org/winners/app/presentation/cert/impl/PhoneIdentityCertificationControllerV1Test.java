package org.winners.app.presentation.cert.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.winners.app.application.cert.PhoneIdentityCertificationService;
import org.winners.app.presentation.ControllerTest;
import org.winners.app.presentation.cert.request.CertifyPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.request.SendPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.response.SendPhoneIdentityOtpNumberResponseDTO;
import org.winners.core.domain.cert.MobileCarrier;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;
import org.winners.core.domain.common.Gender;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PhoneIdentityCertificationControllerV1Test extends ControllerTest {

    public PhoneIdentityCertificationControllerV1Test() {
        super(PhoneIdentityCertificationControllerV1.class);
    }

    @MockBean
    private PhoneIdentityCertificationService phoneIdentityCertificationService;

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 발송")
    void sendPhoneIdentityOtpNumber() {
        UUID certificationKeyId = UUID.randomUUID();
        given(phoneIdentityCertificationService.sendPhoneIdentityOtpNumber(any(SendOtpNumberParameterDTO.class)))
            .willReturn(certificationKeyId);

        SendPhoneIdentityOtpNumberRequestDTO request = new SendPhoneIdentityOtpNumberRequestDTO(
            MobileCarrier.LGM.toString(),
            "신봉교",
            LocalDate.of(1993, 10, 20),
            Gender.MALE.toString(),
            "01012341234");
        SendPhoneIdentityOtpNumberResponseDTO response = new SendPhoneIdentityOtpNumberResponseDTO(certificationKeyId);
        this.postTest("otp-number", request, response);
    }

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 인증")
    void certifyPhoneIdentityOtpNumber() {
        willDoNothing().given(phoneIdentityCertificationService).certifyPhoneIdentityOtpNumber(any(UUID.class), anyString(), anyString());

        CertifyPhoneIdentityOtpNumberRequestDTO request =
            new CertifyPhoneIdentityOtpNumberRequestDTO(UUID.randomUUID(), "01012341234", "000000");
        this.putTest("otp-number", request, null);

        verify(phoneIdentityCertificationService, times(1))
            .certifyPhoneIdentityOtpNumber(request.getCertificationKey(), request.getPhoneNumber(), request.getOptNumber());
    }

}