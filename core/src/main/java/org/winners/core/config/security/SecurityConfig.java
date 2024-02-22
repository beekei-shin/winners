package org.winners.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.winners.core.config.security.token.TokenProvider;
import org.winners.core.config.security.token.TokenRole;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

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
    public OncePerRequestFilter oncePerRequestFilter() {
        return new JwtAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OncePerRequestFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JwtUnAuthenticateHandler(objectMapper);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JwtAccessDeniedHandler(objectMapper);
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
        http.headers().frameOptions().disable()
            .and()
            .httpBasic()
            .and()
            .formLogin().disable()
            .logout().disable()
            .csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers(HttpMethod.GET, SecurityWhitelist.getWhitelistPath(HttpMethod.GET)).permitAll()
                .requestMatchers(HttpMethod.POST, SecurityWhitelist.getWhitelistPath(HttpMethod.POST)).permitAll()
                .requestMatchers(HttpMethod.PUT, SecurityWhitelist.getWhitelistPath(HttpMethod.PUT)).permitAll()
                .requestMatchers(HttpMethod.DELETE, SecurityWhitelist.getWhitelistPath(HttpMethod.DELETE)).permitAll()
                .requestMatchers("/app/**", "/toss/**").hasAnyRole(TokenRole.APP.getRole())
                .requestMatchers("/bo/**").hasAnyRole(TokenRole.BACKOFFICE.getRole())
                .anyRequest().authenticated())
            .addFilterBefore(exceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(oncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler());

        return http.build();
    }

}