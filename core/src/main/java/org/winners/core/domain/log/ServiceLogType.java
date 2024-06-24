package org.winners.core.domain.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum ServiceLogType implements EnumClass {

    UPDATE_PASSWORD("비밀번호 변경"),
    ;

    private final String name;

}
