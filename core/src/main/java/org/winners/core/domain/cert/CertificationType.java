package org.winners.core.domain.cert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertificationType {

    PHONE_IDENTITY("휴대폰 본인인증");

    private final String name;

}
