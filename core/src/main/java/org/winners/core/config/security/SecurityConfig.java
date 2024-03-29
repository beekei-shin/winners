package org.winners.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.winners.core.config.security.filter.JwtAuthenticationFilter;
import org.winners.core.config.security.handler.JwtAccessDeniedHandler;
import org.winners.core.config.security.handler.JwtUnauthorizedHandler;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.config.security.token.TokenRole;
import org.winners.core.domain.user.service.ClientUserDomainService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final ClientUserDomainService clientUserDomainService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .requestMatchers(
                "classpath:/static/**",
                "classpath:/resources/**",
                "classpath:/META-INF/**",
                "/resources/**",
                "/favicon.ico",
                "/api/docs",
                "/api/docs/**",
                "/api/swagger-ui/**",
                "/BOOT-INF/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .headers(hc -> hc
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .authorizeHttpRequests(ar -> ar
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers(HttpMethod.GET, SecurityWhitelist.getWhitelistByMethod(HttpMethod.GET)).permitAll()
                .requestMatchers(HttpMethod.POST, SecurityWhitelist.getWhitelistByMethod(HttpMethod.POST)).permitAll()
                .requestMatchers(HttpMethod.PUT, SecurityWhitelist.getWhitelistByMethod(HttpMethod.PUT)).permitAll()
                .requestMatchers(HttpMethod.DELETE, SecurityWhitelist.getWhitelistByMethod(HttpMethod.DELETE)).permitAll()
                .requestMatchers("/app/**").hasAnyRole(TokenRole.APP.getRole())
                .requestMatchers("/bo/**").hasAnyRole(TokenRole.BACKOFFICE.getRole())
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, clientUserDomainService), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(eh -> eh
                .authenticationEntryPoint(new JwtUnauthorizedHandler(objectMapper))
                .accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper)))
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable);
        return http.build();
    }

}