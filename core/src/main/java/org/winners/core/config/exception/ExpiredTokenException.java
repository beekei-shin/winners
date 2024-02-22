package org.winners.core.config.exception;

import org.winners.core.config.presentation.ApiResponseType;

public class ExpiredTokenException extends TokenException {

    public ExpiredTokenException() {
        super(ApiResponseType.EXPIRED_TOKEN);
    }

}
