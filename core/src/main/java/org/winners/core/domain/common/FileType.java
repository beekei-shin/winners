package org.winners.core.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType implements EnumClass {

    PRODUCT_IMAGE("상품 이미지");

    private final String name;

}
