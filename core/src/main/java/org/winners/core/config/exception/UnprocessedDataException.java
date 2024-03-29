package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class UnprocessedDataException extends ApiException {

    public UnprocessedDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.UNPROCESSED_DATA, exceptionMessageType.getMessage());
    }

}
