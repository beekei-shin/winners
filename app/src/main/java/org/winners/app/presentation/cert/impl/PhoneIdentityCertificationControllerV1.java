package org.winners.app.presentation.cert.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.application.cert.PhoneIdentityCertificationService;
import org.winners.app.presentation.cert.PhoneIdentityCertificationController;
import org.winners.app.presentation.cert.request.CertifyPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.request.SendPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.response.SendPhoneIdentityOtpNumberResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;

import java.util.UUID;


@Tag(name = "101.v1. 휴대폰 본인인증")
@RestController
@RequestMapping(value = "v1/cert/phone-identity")
@RequiredArgsConstructor
public class PhoneIdentityCertificationControllerV1 implements PhoneIdentityCertificationController {

    private final PhoneIdentityCertificationService phoneIdentityCertificationServiceV1;

    @Override
    public ApiResponse<SendPhoneIdentityOtpNumberResponseDTO> sendPhoneIdentityOtpNumber(SendPhoneIdentityOtpNumberRequestDTO request) {
        UUID certificationKey = phoneIdentityCertificationServiceV1.sendPhoneIdentityOtpNumber(SendOtpNumberParameterDTO.builder()
            .name(request.getName())
            .birthday(request.getBirthday())
            .gender(request.getGender())
            .mobileCarrier(request.getMobileCarrier())
            .phoneNumber(request.getPhoneNumber())
            .build());
        return ApiResponse.success(new SendPhoneIdentityOtpNumberResponseDTO(certificationKey));
    }

    @Override
    public ApiResponse<?> certifyPhoneIdentityOtpNumber(CertifyPhoneIdentityOtpNumberRequestDTO request) {
        phoneIdentityCertificationServiceV1.certifyPhoneIdentityOtpNumber(
            request.getCertificationKey(),
            request.getPhoneNumber(),
            request.getPinNumber());
        return ApiResponse.success();
    }

}
