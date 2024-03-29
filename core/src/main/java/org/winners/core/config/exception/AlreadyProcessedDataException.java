package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class AlreadyProcessedDataException extends ApiException {

    public AlreadyProcessedDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.ALREADY_PROCESSED_DATA, exceptionMessageType.getMessage());
    }

}
