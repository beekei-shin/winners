package org.winners.core.domain.cert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum MobileCarrier implements EnumClass {

    SKT("SK 텔레콤"),
    KTF("KT"),
    LGM("LG U+"),
    SKT_SMART_SAVE("SK 텔레콤 알뜰폰"),
    KTF_SMART_SAVE("KT 알뜰폰"),
    LGM_SMART_SAVE("LG U+ 알뜰폰");

    private final String name;

}
