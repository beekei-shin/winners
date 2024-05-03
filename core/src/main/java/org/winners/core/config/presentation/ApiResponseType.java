package org.winners.core.config.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiResponseType {

    SUCCESS(HttpStatus.OK,                                      "20000", "API 호출 성공"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST,                         "40000", "잘못된 API 요청"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE,   "415000", "지원하지 않는 Media Type"),

    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED,                 "40101", "토큰 인증 실패"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,                      "40102", "토큰 유효기간 만료"),
    FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN,                       "40300", "권한 없음"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,     "50000", "알수없는 오류"),
    SECURITY_ERROR(HttpStatus.BAD_GATEWAY,                      "50200", "보안 오류"),
    DUPLICATED_DATA(HttpStatus.BAD_GATEWAY,                     "50201", "중복된 데이터"),
    EXPIRED_DATA(HttpStatus.BAD_GATEWAY,                        "50202", "만료된 데이터"),
    NOT_EXIST_DATA(HttpStatus.BAD_GATEWAY,                      "50203", "존재하지 않는 데이터"),
    INCORRECT_DATA(HttpStatus.BAD_GATEWAY,                      "50204", "일치하지 않는 데이터"),
    NOT_ACCESS_DATA(HttpStatus.BAD_GATEWAY,                     "50205", "허용되지 않은 데이터"),
    UNPROCESSED_DATA(HttpStatus.BAD_GATEWAY,                    "50206", "처리되지 않은 데이터"),
    ALREADY_PROCESSED_DATA(HttpStatus.BAD_GATEWAY,              "50207", "이미 처리된 데이터"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
