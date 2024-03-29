package org.winners.core.config.security;


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
public class JwtUnAuthenticateHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        try (OutputStream os = response.getOutputStream()) {
            final ApiResponseType apiResponseType = ApiResponseType.valueOf(
                String.valueOf(Optional.ofNullable(request.getAttribute("ExceptionType"))
                    .map(exceptionType -> ApiResponseType.valueOf(String.valueOf(exceptionType)))
                    .orElse(ApiResponseType.UNAUTHORIZED_TOKEN)));

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(apiResponseType.getHttpStatus().value());

            objectMapper.writeValue(os, ApiResponse.exception(apiResponseType));
            os.flush();
        }
    }

}
