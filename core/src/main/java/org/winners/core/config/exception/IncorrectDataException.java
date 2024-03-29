package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class IncorrectDataException extends ApiException {

    public IncorrectDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.INCORRECT_DATA, exceptionMessageType.getMessage());
    }

}
