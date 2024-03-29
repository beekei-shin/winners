package org.winners.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    ACTIVE("활성화"),
    BLOCK("차단");

    private final String name;

}
