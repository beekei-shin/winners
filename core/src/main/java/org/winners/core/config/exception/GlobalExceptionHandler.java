package org.winners.core.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.ApiResponseType;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 지원하지 않는 Media type Exception
     */
    @ExceptionHandler(value = {
        HttpMediaTypeNotSupportedException.class,
        HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<ApiResponse<?>> unSupportedMediaTypeExceptionHandler(Exception e) {
        final ApiResponseType apiResponseType = ApiResponseType.UNSUPPORTED_MEDIA_TYPE;
        return ResponseEntity
            .status(apiResponseType.getHttpStatus())
            .body(ApiResponse.exception(apiResponseType));
    }

    /**
     * 잘못된 API 요청 Exception
     */
    @ExceptionHandler(value = {
        MethodArgumentNotValidException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class,
        MissingServletRequestParameterException.class
    })
    public ResponseEntity<ApiResponse<?>> badRequestExceptionHandler(Exception e) {
        final ApiResponseType apiResponseType = ApiResponseType.BAD_REQUEST;
        return ResponseEntity
            .status(apiResponseType.getHttpStatus())
            .body(ApiResponse.exception(apiResponseType));
    }

    /**
     * API Exception
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse<?>> apiExceptionHandler(ApiException e){
        return ResponseEntity
            .status(e.getApiResponseType().getHttpStatus())
            .body(ApiResponse.exception(e.getApiResponseType(), e.getMessage()));
    }

}
