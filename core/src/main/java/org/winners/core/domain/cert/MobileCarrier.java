package org.winners.core.domain.cert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum MobileCarrier implements EnumClass {

    SK("SK 텔레콤"),
    KT("KT"),
    LG("LG U+"),
    SK_SMART_SAVE("SK 텔레콤 알뜰폰"),
    KT_SMART_SAVE("KT 알뜰폰"),
    LG_SMART_SAVE("LG U+ 알뜰폰");

    private final String name;

}
