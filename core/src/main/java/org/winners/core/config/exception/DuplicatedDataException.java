package org.winners.core.config.exception;


import org.winners.core.config.presentation.ApiResponseType;

public class DuplicatedDataException extends ApiException {

    public DuplicatedDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.DUPLICATED_DATA, exceptionMessageType.getMessage());
    }

}
