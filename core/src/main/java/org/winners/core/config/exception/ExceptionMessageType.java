package org.winners.core.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.domain.common.EnumClass;

@Getter
@AllArgsConstructor
public enum ExceptionMessageType implements EnumClass {

    NOT_EXIST_CERTIFICATION_KEY("등록된 인증키 정보가 없습니다."),
    NOT_CERTIFIED_CERTIFICATION_KEY("인증되지 않은 인증키입니다."),
    ALREADY_CERTIFIED_CERTIFICATION_KEY("이미 인증된 인증키입니다."),
    ALREADY_USED_CERTIFICATION_KEY("이미 사용한 인증키입니다."),
    EXPIRED_CERTIFICATION_KEY("만료된 인증키입니다."),

    NOT_EXIST_PHONE_IDENTITY_CERTIFICATION_HISTORY("휴대폰 본인인증 내역이 없습니다."),
    INVALID_OTP_NUMBER("OTP 번호가 일치하지 않습니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),

    NOT_EXIST_USER("존재하지 않는 회원입니다."),
    BLOCK_USER("차단된 회원입니다."),
    RESIGN_USER("탈퇴된 회원입니다."),
    DUPLICATED_PHONE_NUMBER("중복된 휴대폰 번호 입니다."),
    DUPLICATED_CI("중복된 CI 입니다."),

    NOT_EXIST_SHOP("존재하지 않는 상점입니다."),
    DUPLICATED_SHOP("중복된 상점입니다."),

    ;

    private final String message;

}
