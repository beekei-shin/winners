package org.winners.core.config.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ApiResponse<D> {

    private String responseCode;

    private String responseMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private D responseData;

    public static ApiResponse<?> success() {
        final ApiResponseType responseType = ApiResponseType.SUCCESS;
        return ApiResponse.builder()
            .responseCode(responseType.getCode())
            .responseMessage(responseType.getMessage())
            .build();
    }

    public static <D> ApiResponse<D> success(D responseData) {
        final ApiResponseType responseType = ApiResponseType.SUCCESS;
        return ApiResponse.<D>builder()
            .responseCode(responseType.getCode())
            .responseMessage(responseType.getMessage())
            .responseData(responseData)
            .build();
    }

    public static ApiResponse<?> exception(ApiResponseType responseType) {
        return ApiResponse.exception(responseType, null);
    }

    public static ApiResponse<?> exception(ApiResponseType responseType, String responseMessage) {
        return ApiResponse.builder()
            .responseCode(responseType.getCode())
            .responseMessage(Optional.ofNullable(responseMessage).orElse(responseType.getMessage()))
            .build();
    }

}
