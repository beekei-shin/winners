package org.winners.core.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessageType {

    NOT_EXIST_AUTHENTICATION_KEY("등록된 인증키 정보가 없습니다."),
    NOT_CERTIFIED_AUTHENTICATION_KEY("인증되지 않은 인증키입니다."),
    ALREADY_CERTIFIED_AUTHENTICATION_KEY("이미 인증된 인증키입니다."),
    ALREADY_USED_AUTHENTICATION_KEY("이미 사용한 인증키입니다."),
    EXPIRED_AUTHENTICATION_KEY("만료된 인증키입니다."),

    NOT_EXIST_PHONE_IDENTITY_CERTIFICATION_HISTORY("휴대폰 본인인증 내역이 없습니다."),
    INCORRECT_OTP_NUMBER("OTP 번호가 일치하지 않습니다."),

    NOT_EXIST_USER("존재하지 않는 회원입니다."),
    BLOCK_USER("차단된 회원입니다."),
    RESIGN_USER("탈퇴된 회원입니다."),
    DUPLICATED_PHONE_NUMBER("중복된 휴대폰 번호 입니다."),
    DUPLICATED_CI("중복된 CI 입니다."),
    ;

    private final String message;

}
