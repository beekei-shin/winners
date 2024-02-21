package org.winners.core.config.exception;


import org.winners.core.config.presentation.ApiResponseType;

public class NotExistDataException extends ApiException {

    public NotExistDataException() {
        super(ApiResponseType.NOT_EXIST_DATA);
    }

    public NotExistDataException(String message) {
        super(ApiResponseType.NOT_EXIST_DATA, message);
    }

}
