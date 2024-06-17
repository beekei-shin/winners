package org.winners.core.config.security.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum TokenRole implements EnumClass {

    APP("앱 권한", "ROLE_APP"),
    BACKOFFICE("백오피스 권한", "ROLE_BACKOFFICE"),
    CLIENT_USER("사용자 회원 권한", "ROLE_CLIENT_USER"),
    BUSINESS_USER("비지니스 회원 권한", "ROLE_BUSINESS_USER"),
    ADMIN_USER("관리자 회원 권한", "ROLE_ADMIN_USER"),
    ;

    private final String name;
    private final String role;

}
