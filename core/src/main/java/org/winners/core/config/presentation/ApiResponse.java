package org.winners.core.config.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ApiResponse<D> {

    private String code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private D data;

    public static ApiResponse<?> success() {
        final ApiResponseType responseType = ApiResponseType.SUCCESS;
        return ApiResponse.builder()
            .code(responseType.getCode())
            .message(responseType.getMessage())
            .build();
    }

    public static <D> ApiResponse<D> success(D data) {
        final ApiResponseType responseType = ApiResponseType.SUCCESS;
        return ApiResponse.<D>builder()
            .code(responseType.getCode())
            .message(responseType.getMessage())
            .data(data)
            .build();
    }

}
