package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class ExpiredDataException extends ApiException {

    public ExpiredDataException(ExceptionMessageType exceptionMessageType) {
        super(ApiResponseType.EXPIRED_DATA, exceptionMessageType.getMessage());
    }

}
