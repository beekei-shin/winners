package org.winners.core.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.config.presentation.ApiResponseType;

@Getter
@AllArgsConstructor
public class TokenException extends RuntimeException {

    private final ApiResponseType apiResponseType;

}
