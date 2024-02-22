package org.winners.core.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.winners.core.config.exception.ExpiredTokenException;
import org.winners.core.config.exception.UnauthorizedTokenException;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenProvider;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider jwtProvider;
    private final static String TOKEN_HEADER_NAME = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String accessToken = request.getHeader(TOKEN_HEADER_NAME);
            if (StringUtils.isNotBlank(accessToken)) {
                final String prefix = accessToken.trim().substring(0, TOKEN_PREFIX.length()).toUpperCase();
                if (!TOKEN_PREFIX.equalsIgnoreCase(prefix)) throw new RuntimeException("Header Prefix Token Type");

                final String token = accessToken.trim().substring(TOKEN_PREFIX.length());
                if (jwtProvider.validateAccessToken(token)) {
                    final UsernamePasswordAuthenticationToken authenticationToken;
                    authenticationToken = new UsernamePasswordAuthenticationToken(jwtProvider.getAccessTokenId(token), null, jwtProvider.getAccessTokenAuthorities(token));
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (ExpiredTokenException e) {
            request.setAttribute("ExceptionType", ApiResponseType.EXPIRED_TOKEN.toString());
            throw e;
        } catch (UnauthorizedTokenException e) {
            request.setAttribute("ExceptionType", ApiResponseType.UNAUTHORIZED_TOKEN.toString());
            throw e;
        }

        filterChain.doFilter(request, response);
    }

}
