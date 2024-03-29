package org.winners.core.config.exception;


import org.winners.core.config.presentation.ApiResponseType;

public class NotExistDataException extends ApiException {

    public NotExistDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.NOT_EXIST_DATA, exceptionMessageType.getMessage());
    }

}
