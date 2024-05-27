package org.winners.core.domain.cert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum CertificationType implements EnumClass {

    PHONE_IDENTITY("휴대폰 본인인증");

    private final String name;

}
