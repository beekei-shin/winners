package org.winners.core.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.winners.core.config.exception.ExpiredTokenException;
import org.winners.core.config.exception.UnauthorizedTokenException;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.service.ClientUserDomainService;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String TOKEN_HEADER_NAME = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final SecurityWhitelist securityWhitelist;
    private final ClientUserDomainService clientUserDomainService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        return securityWhitelist.isWhitelist(contextPath, requestURI, method);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String accessToken = request.getHeader(TOKEN_HEADER_NAME);
            if (StringUtils.isNotBlank(accessToken)) {
                String prefix = accessToken.trim().substring(0, TOKEN_PREFIX.length()).toUpperCase();
                if (!TOKEN_PREFIX.equalsIgnoreCase(prefix)) throw new RuntimeException("Header Prefix Token Type");

                final String token = accessToken.trim().substring(TOKEN_PREFIX.length());
                if (tokenProvider.validateAccessToken(token)) {
                    long userId = tokenProvider.getAccessTokenId(token);
                    Collection<? extends GrantedAuthority> authority = tokenProvider.getAccessTokenAuthorities(token);

                    ClientUser clientUser = clientUserDomainService.getClientUser(userId);
                    clientUser.accessUserCheck();

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authority);
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
        } finally {
            filterChain.doFilter(request, response);
        }
    }

}
