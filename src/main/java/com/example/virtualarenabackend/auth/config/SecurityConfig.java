package com.example.virtualarenabackend.auth.config;

import com.example.virtualarenabackend.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Value("${application.security.front-url}")
    private String FRONT_URL;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(FRONT_URL));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                // Public authentication endpoints
                .requestMatchers(
                        "/api/v1/auth/register",
                        "/api/v1/auth/authenticate",
                        "/api/v1/auth/verify-email/send",
                        "/api/v1/auth/verify-email/verify",
                        "/api/v1/auth/refresh-token"
                ).permitAll()
                // Public article endpoints - GET methods
                .requestMatchers(HttpMethod.GET,
                        "/article/featured",
                        "/article/latest",
                        "/article/search",
                        "/article/{articleId}",
                        "/article/{articleId}/comments",
                        "/comment/{commentId}/replies"
                ).permitAll()
                // Swagger/OpenAPI endpoints
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                ).permitAll()
                // Protected POST/PUT/DELETE endpoints
                .requestMatchers(HttpMethod.POST,
                        "/api/v1/auth/me/update",
                        "/api/v1/auth/change-password",
                        "/article/{articleId}/comments",
                        "/article",
                        "/article/{articleId}/heart",
                        "/article/{articleId}/bookmark",
                        "/comment/{commentId}/replies"
                ).authenticated()
                .requestMatchers(HttpMethod.PUT,
                        "article/{articleId}"
                ).authenticated()
                .requestMatchers(HttpMethod.DELETE,
                        "/comment/{commentId}",
                        "/{replyId}/reply"
                ).authenticated()
                // User profile endpoints
                .requestMatchers(HttpMethod.GET,
                        "/api/v1/auth/me",
                        "/article/me"
                ).authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}