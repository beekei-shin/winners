package org.winners.core.config.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum TokenRole implements EnumClass {

    APP("앱 권한", "APP"),
    BACKOFFICE("백오피스 권한", "BACKOFFICE"),
    CLIENT_USER("사용자 회원 권한", "CLIENT_USER"),
    ADMIN_USER("관리자 회원 권한", "ADMIN_USER"),
    ;

    private final String name;
    private final String role;

}
