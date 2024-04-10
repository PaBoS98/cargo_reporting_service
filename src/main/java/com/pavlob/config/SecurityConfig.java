package com.pavlob.config;

import com.pavlob.config.jwt.JwtTokenProvider;
import com.pavlob.filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(getAllowedPaths()).permitAll()
                        .requestMatchers(getSecuredPaths()).authenticated())
                .authenticationProvider(tokenAuthenticationProvider())
                .addFilterBefore(jwtTokenAuthenticationFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    static public String[] getAllowedPaths() {
        return new String[] { "/api/token/**", "/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**"};
    }

    static public String[] getSecuredPaths() {
        return new String[] { "/api/**"};
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtTokenProvider);
    }
}
