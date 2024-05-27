package org.winners.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum UserStatus implements EnumClass {

    ACTIVE("활성화"),
    RESIGN("탈퇴"),
    BLOCK("차단");

    private final String name;

}
