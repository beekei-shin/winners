package com.winners.appApi.presentation.cert.v1;

import com.winners.appApi.presentation.cert.v1.PhoneIdentityCertificationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import com.winners.appApi.application.cert.PhoneIdentityCertificationService;
import com.winners.appApi.config.ControllerTest;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.domain.cert.MobileCarrier;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;
import org.winners.core.domain.common.Gender;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PhoneIdentityCertificationControllerTest extends ControllerTest {

    public PhoneIdentityCertificationControllerTest() {
        super(PhoneIdentityCertificationController.class);
    }

    @MockBean
    private PhoneIdentityCertificationService phoneIdentityCertificationService;

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 발송")
    void sendPhoneIdentityOtpNumber() {
        UUID certificationKeyId = UUID.randomUUID();
        given(phoneIdentityCertificationService.sendPhoneIdentityOtpNumber(any(SendOtpNumberParameterDTO.class)))
            .willReturn(certificationKeyId);

        MobileCarrier mobileCarrier = MobileCarrier.LGM;
        String userName = "신봉교";
        LocalDate birthday = LocalDate.of(1993, 10, 20);
        Gender gender = Gender.MALE;
        String phoneNumber = "01012341234";

        mvcTest("otp-number", HttpMethod.POST)
            .requestBody(
                Map.entry("mobileCarrier", mobileCarrier.toString()),
                Map.entry("userName", userName),
                Map.entry("birthday", birthday),
                Map.entry("gender", gender),
                Map.entry("phoneNumber", phoneNumber))
            .responseBody(Map.entry("certificationKey", certificationKeyId.toString()))
            .responseType(ApiResponseType.SUCCESS)
            .run();
    }

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 인증")
    void certifyPhoneIdentityOtpNumber() {
        willDoNothing().given(phoneIdentityCertificationService).certifyPhoneIdentityOtpNumber(any(UUID.class), anyString(), anyString());

        UUID certificationKey = UUID.randomUUID();
        String phoneNumber = "01012341234";
        String optNumber = "000000";

        mvcTest("otp-number", HttpMethod.PUT)
            .requestBody(
                Map.entry("certificationKey", certificationKey.toString()),
                Map.entry("phoneNumber", phoneNumber),
                Map.entry("optNumber", optNumber))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(phoneIdentityCertificationService, times(1))
            .certifyPhoneIdentityOtpNumber(certificationKey, phoneNumber, optNumber);
    }

}