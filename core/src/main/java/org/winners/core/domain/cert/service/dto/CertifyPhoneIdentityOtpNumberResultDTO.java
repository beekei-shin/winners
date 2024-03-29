package org.winners.core.domain.cert.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CertifyPhoneIdentityOtpNumberResultDTO {

    private final boolean isCertified;
    private final String notCertifiedReason;

    public static CertifyPhoneIdentityOtpNumberResultDTO certifySuccess() {
        return CertifyPhoneIdentityOtpNumberResultDTO.builder()
            .isCertified(true)
            .build();
    }

    public static CertifyPhoneIdentityOtpNumberResultDTO certifyFail(String notCertifiedReason) {
        return CertifyPhoneIdentityOtpNumberResultDTO.builder()
            .isCertified(false)
            .notCertifiedReason(notCertifiedReason)
            .build();
    }

}
