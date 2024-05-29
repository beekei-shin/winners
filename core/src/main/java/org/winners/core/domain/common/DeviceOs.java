package org.winners.core.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceOs implements EnumClass {

    AOS("안드로이드"),
    IOS("IOS");

    private final String name;

}
