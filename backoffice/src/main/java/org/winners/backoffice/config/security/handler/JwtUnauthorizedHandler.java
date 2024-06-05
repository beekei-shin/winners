package org.winners.backoffice.config.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.ApiResponseType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtUnauthorizedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        e.printStackTrace();
        try (OutputStream os = response.getOutputStream()) {
            final ApiResponseType apiResponseType = ApiResponseType.valueOf(
                String.valueOf(Optional.ofNullable(request.getAttribute("ExceptionType"))
                    .map(exceptionType -> ApiResponseType.valueOf(String.valueOf(exceptionType)))
                    .orElse(ApiResponseType.INTERNAL_SERVER_ERROR)));

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(apiResponseType.getHttpStatus().value());

            objectMapper.writeValue(os, ApiResponse.exception(apiResponseType));
            os.flush();
        }
    }

}
