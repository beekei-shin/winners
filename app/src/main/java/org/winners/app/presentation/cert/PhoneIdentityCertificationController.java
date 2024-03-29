package org.winners.app.presentation.cert;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.winners.app.presentation.cert.request.CertifyPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.request.SendPhoneIdentityOtpNumberRequestDTO;
import org.winners.app.presentation.cert.response.SendPhoneIdentityOtpNumberResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

public interface PhoneIdentityCertificationController {

    @Operation(summary = "휴대폰 본인인증 OTP 번호 발송")
    @PostMapping(name = "휴대폰 본인인증 OTP 번호 발송", value = "otp-number", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<SendPhoneIdentityOtpNumberResponseDTO> sendPhoneIdentityOtpNumber(@RequestBody @Valid SendPhoneIdentityOtpNumberRequestDTO request);

    @Operation(summary = "휴대폰 본인인증 OTP 번호 인증")
    @PutMapping(name = "휴대폰 본인인증 OTP 번호 인증", value = "otp-number", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> certifyPhoneIdentityOtpNumber(@RequestBody @Valid CertifyPhoneIdentityOtpNumberRequestDTO request);

}
