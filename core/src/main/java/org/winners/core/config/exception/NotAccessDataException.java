package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class NotAccessDataException extends ApiException {

    public NotAccessDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.NOT_ACCESS_DATA, exceptionMessageType.getMessage());
    }

}
