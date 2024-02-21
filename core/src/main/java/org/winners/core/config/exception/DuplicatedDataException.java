package org.winners.core.config.exception;


import org.winners.core.config.presentation.ApiResponseType;

public class DuplicatedDataException extends ApiException{

    public DuplicatedDataException() {
        super(ApiResponseType.DUPLICATED_DATA);
    }

    public DuplicatedDataException(String message) {
        super(ApiResponseType.DUPLICATED_DATA, message);
    }

}
