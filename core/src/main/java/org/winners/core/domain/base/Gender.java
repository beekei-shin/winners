package org.winners.core.domain.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.enums.EnumClass;

@Getter
@AllArgsConstructor
public enum Gender implements EnumClass {

    MALE("남성"),
    FEMALE("여성");

    private final String name;

}
