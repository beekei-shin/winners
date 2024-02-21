package org.winners.core.config.exception;

import lombok.Getter;
import org.winners.core.config.presentation.ApiResponseType;

@Getter
public class ApiException extends RuntimeException {

    private final ApiResponseType apiResponseType;
    private final String message;

    public ApiException(ApiResponseType apiResponseType) {
        this.apiResponseType = apiResponseType;
        this.message = null;
    }

    public ApiException(ApiResponseType apiResponseType, String message) {
        this.apiResponseType = apiResponseType;
        this.message = message;
    }

}
