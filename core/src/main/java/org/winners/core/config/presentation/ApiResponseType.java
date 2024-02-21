package org.winners.core.config.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiResponseType {

    SUCCESS(HttpStatus.OK, "20001", "API 호출 성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "40001", "잘못된 API 요청"),
    NOT_EXIST_DATA(HttpStatus.INTERNAL_SERVER_ERROR, "50001", "존재하지 않는 데이터"),
    DUPLICATED_DATA(HttpStatus.INTERNAL_SERVER_ERROR, "50002", "중복된 데이터"),
    ALREADY_PROCESSED_DATA(HttpStatus.INTERNAL_SERVER_ERROR, "50003", "이미 처리된 데이터"),
    EXPIRED_DATA(HttpStatus.INTERNAL_SERVER_ERROR, "50004", "만료된 데이터"),
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "40101", "토큰 인증 실패"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "40102", "토큰 유효기간 만료"),
    FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN, "40301", "권한 없음");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
