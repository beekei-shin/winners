package org.winners.app.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.ApiResponseType;

import java.io.IOException;
import java.io.OutputStream;

@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        try (OutputStream os = response.getOutputStream()) {
            final ApiResponseType apiResponseType = ApiResponseType.FORBIDDEN_TOKEN;

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(apiResponseType.getHttpStatus().value());

            objectMapper.writeValue(os, ApiResponse.exception(apiResponseType));
            os.flush();
        }
    }

}
