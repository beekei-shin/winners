package org.winners.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    CLIENT("사용자 회원"),
    ADMIN("관리자 회원");

    private final String name;

}
