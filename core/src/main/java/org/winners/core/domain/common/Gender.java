package org.winners.core.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender implements EnumClass {

    MALE("남성"),
    FEMALE("여성");

    private final String name;

}
