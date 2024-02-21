package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class AlreadyProcessedData extends ApiException {

    public AlreadyProcessedData() {
        super(ApiResponseType.ALREADY_PROCESSED_DATA);
    }

    public AlreadyProcessedData(String message) {
        super(ApiResponseType.ALREADY_PROCESSED_DATA, message);
    }

}
