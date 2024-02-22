package org.winners.core.config.security.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {

    ACCESS("엑세스 토큰", 1000 * 60 * 60 * 24), // 1일
    REFRESH("엑세스 토큰", 1000 * 60 * 60 * 24 * 14); // 14일

    private final String name;
    private final long expiresIn;


}
