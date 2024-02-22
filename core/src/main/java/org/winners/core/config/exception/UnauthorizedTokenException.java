package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class UnauthorizedTokenException extends TokenException {

    public UnauthorizedTokenException() {
        super(ApiResponseType.UNAUTHORIZED_TOKEN);
    }

}
