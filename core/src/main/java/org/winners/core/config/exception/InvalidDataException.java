package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class InvalidDataException extends ApiException {

    public InvalidDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.INVALID_DATA, exceptionMessageType.getMessage());
    }

}
