package org.winners.core.domain.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopStatus {

    UNOPEN("미오픈"),
    OPEN("오픈"),
    CLOSE("페업"),
    DELETE("삭제"),
    ;

    private final String name;

}
