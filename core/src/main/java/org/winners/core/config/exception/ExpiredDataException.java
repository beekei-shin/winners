package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class ExpiredDataException extends ApiException {

    public ExpiredDataException() {
        super(ApiResponseType.EXPIRED_DATA);
    }

    public ExpiredDataException(String message) {
        super(ApiResponseType.EXPIRED_DATA, message);
    }

}
