package org.winners.core.config.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
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

import java.util.Objects;
import java.util.Optional;

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
        e.printStackTrace();
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
        e.printStackTrace();
        if (e.getClass().equals(MethodArgumentNotValidException.class)) {
            MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) e;
            String fieldName = Objects.requireNonNull(notValidException.getFieldError()).getField();
            String message = Optional.ofNullable(notValidException.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(apiResponseType.getMessage());
            return ResponseEntity
                .status(apiResponseType.getHttpStatus())
                .body(ApiResponse.exception(apiResponseType, fieldName + " : " + message));
        } else {
            return ResponseEntity
                .status(apiResponseType.getHttpStatus())
                .body(ApiResponse.exception(apiResponseType));
        }
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

    /**
     * Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> exceptionHandler(Exception e){
        e.printStackTrace();
        ApiResponseType apiResponseType = ApiResponseType.INTERNAL_SERVER_ERROR;
        return ResponseEntity
            .status(apiResponseType.getHttpStatus())
            .body(ApiResponse.exception(apiResponseType, apiResponseType.getMessage()));
    }

}
