package org.winners.core.config.security.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenRole {

    APP("앱 권한", "APP"),
    BACKOFFICE("백오피스 토큰", "BACKOFFICE");

    private final String name;
    private final String role;

}
