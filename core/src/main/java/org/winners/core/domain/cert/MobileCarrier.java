package org.winners.core.domain.cert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MobileCarrier {

    SK("SK 텔레콤"),
    KT("KT"),
    LG("LG U+"),
    SK_SMART_SAVE("SK 텔레콤 알뜰폰"),
    KT_SMART_SAVE("KT 알뜰폰"),
    LG_SMART_SAVE("LG U+ 알뜰폰");

    private final String name;

}
