package org.winners.core.domain.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum ShopType implements EnumClass {

    RESTAURANT("식당");

    private final String name;

}
