package org.winners.core.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum BoardType implements EnumClass {

    NOTICE("공지사항"),
    INQUIRY("1:1 문의");

    private final String name;

}
