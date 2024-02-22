package org.winners.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.winners.core.config.exception.TokenException;
import org.winners.core.config.presentation.ApiResponse;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenException e){
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, TokenException exception) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(exception.getApiResponseType().getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.exception(exception.getApiResponseType())));
    }

}
