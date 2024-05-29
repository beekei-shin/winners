package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class CannotProcessedDataException extends ApiException {

    public CannotProcessedDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.CANNOT_PROCESSED_DATA, exceptionMessageType.getMessage());
    }

}
