package org.winners.core.config.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.winners.core.config.exception.ExpiredTokenException;
import org.winners.core.config.exception.UnauthorizedTokenException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    @Value("${spring.security.access-token.secret-key}")
    private String accessTokenSecretKey;

    @Value("${spring.security.refresh-token.secret-key}")
    private String refreshTokenSecretKey;

    private final String tokenTypeKey = "tokenType";
    private final String authorityKey = "authorities";
    private final String authorityRegex = ",";


    private String issueToken(Long id, Claims claims, String secretKey, long expiredTime) {
        claims.setSubject(String.valueOf(id));
        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setClaims(claims)
            .setIssuer("Winners")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public String issueAccessToken(long id, Set<TokenRole> authorities) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(tokenTypeKey, TokenType.ACCESS.toString());
        claims.put(authorityKey, authorities.stream().map(TokenRole::getRole).reduce("", (a, b) -> StringUtils.isNotBlank(a) ? a + authorityRegex + "ROLE_" + b : "ROLE_" + b));
        return issueToken(id, Jwts.claims(claims), accessTokenSecretKey, TokenType.ACCESS.getExpiresIn());
    }

    public String issueRefreshToken(long id, Set<TokenRole> authorities) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(tokenTypeKey, TokenType.REFRESH.toString());
        claims.put(authorityKey, authorities.stream().map(TokenRole::getRole).reduce("", (a, b) -> StringUtils.isNotBlank(a) ? a + authorityRegex + "ROLE_" + b : "ROLE_" + b));
        return issueToken(id, Jwts.claims(claims), refreshTokenSecretKey, TokenType.REFRESH.getExpiresIn());
    }

    private Claims getClaim(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Claims getAccessTokenClaim(String token) {
        return getClaim(token, accessTokenSecretKey);
    }

    public Claims getRefreshTokenClaim(String token) {
        return getClaim(token, refreshTokenSecretKey);
    }

    public Claims getWebTokenClaim(String token) {
        return getClaim(token, accessTokenSecretKey);
    }

    public Long getAccessTokenId(String token) {
        return Optional.ofNullable(getAccessTokenClaim(token).getSubject()).map(Long::parseLong).orElse(null);
    }

    public Long getRefreshTokenId(String token) {
        return Optional.ofNullable(getRefreshTokenClaim(token).getSubject()).map(Long::parseLong).orElse(null);
    }

    public LocalDateTime getAccessTokenExpiredDate(String token) {
        final Date expiredDate = getAccessTokenClaim(token).getExpiration();
        return new java.sql.Timestamp(expiredDate.getTime()).toLocalDateTime();
    }

    public LocalDateTime getRefreshTokenExpiredDate(String token) {
        final Date expiredDate = getRefreshTokenClaim(token).getExpiration();
        return new java.sql.Timestamp(expiredDate.getTime()).toLocalDateTime();
    }

    public Collection<? extends GrantedAuthority> getAccessTokenAuthorities(String token) {
        final String authorities = String.valueOf(getAccessTokenClaim(token).get(authorityKey));
        return Arrays.stream(authorities.split(authorityRegex))
            .map(auth -> (GrantedAuthority) () -> auth)
            .collect(Collectors.toList());
    }

    public Collection<? extends GrantedAuthority> getRefreshTokenAuthorities(String token) {
        final String authorities = String.valueOf(getRefreshTokenClaim(token).get(authorityKey));
        return Arrays.stream(authorities.split(authorityRegex))
            .map(auth -> (GrantedAuthority) () -> auth)
            .collect(Collectors.toList());
    }

    public TokenType getTokenType(Claims claims) {
        return TokenType.valueOf(claims.get(tokenTypeKey).toString());
    }

    private Boolean validateToken(String token, String secretKey) {
        try {
            getClaim(token, secretKey);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new UnauthorizedTokenException();
        }
    }

    public Boolean validateAccessToken(String token) {
        return validateToken(token, accessTokenSecretKey);
    }

    public Boolean validateRefreshToken(String token) {
        return validateToken(token, refreshTokenSecretKey);
    }

}
