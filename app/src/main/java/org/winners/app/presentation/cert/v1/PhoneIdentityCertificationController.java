package org.winners.app.presentation.cert.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.winners.app.application.cert.PhoneIdentityCertificationService;
import org.winners.app.presentation.AppController;
import org.winners.app.presentation.cert.v1.request.CertifyPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.v1.request.SendPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.v1.response.SendPhoneIdentityOtpNumberResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.version.ApiVersion;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;

import java.util.UUID;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = AppController.PHONE_IDENTITY_CERT_TAG_NAME)
@RequestMapping(path = AppController.PHONE_IDENTITY_CERT_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PhoneIdentityCertificationController {

    private final PhoneIdentityCertificationService phoneIdentityCertificationServiceV1;

    @Operation(summary = "휴대폰 본인인증 OTP 번호 발송")
    @PostMapping(name = "휴대폰 본인인증 OTP 번호 발송", path = "otp-number")
    public ApiResponse<SendPhoneIdentityOtpNumberResponseDTO> sendPhoneIdentityOtpNumber(@RequestBody @Valid SendPhoneIdentityOtpNumberRequestDTO request) {
        UUID certificationKey = phoneIdentityCertificationServiceV1.sendPhoneIdentityOtpNumber(SendOtpNumberParameterDTO.builder()
            .userName(request.getUserName())
            .birthday(request.getBirthday())
            .gender(request.getGender())
            .mobileCarrier(request.getMobileCarrier())
            .phoneNumber(request.getPhoneNumber())
            .build());
        return ApiResponse.success(new SendPhoneIdentityOtpNumberResponseDTO(certificationKey));
    }

    @Operation(summary = "휴대폰 본인인증 OTP 번호 인증")
    @PutMapping(name = "휴대폰 본인인증 OTP 번호 인증", path = "/otp-number", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> certifyPhoneIdentityOtpNumber(@RequestBody @Valid CertifyPhoneIdentityOtpNumberRequestDTO request) {
        phoneIdentityCertificationServiceV1.certifyPhoneIdentityOtpNumber(
            request.getCertificationKey(),
            request.getPhoneNumber(),
            request.getOptNumber());
        return ApiResponse.success();
    }

}

