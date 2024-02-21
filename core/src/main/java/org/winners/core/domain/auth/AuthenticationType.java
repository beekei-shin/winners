package org.winners.core.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationType {

    PHONE("휴대폰 인증");

    private final String name;

}
