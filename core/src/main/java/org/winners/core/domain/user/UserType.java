package org.winners.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum UserType implements EnumClass {

    CLIENT("사용자 회원"),
    ADMIN("관리자 회원");

    private final String name;

}
