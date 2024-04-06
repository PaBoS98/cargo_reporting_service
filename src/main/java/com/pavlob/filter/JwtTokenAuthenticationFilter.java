package com.pavlob.filter;

import com.pavlob.config.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.pavlob.Constant.BEARER_PREFIX;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
                                                                                    throws IOException, ServletException {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(token) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
            final String jwt = token.substring(BEARER_PREFIX.length());

            if (jwtTokenProvider.isJwtTokenValid(jwt)) {
                final PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(jwt, token);
                final Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
                if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
                    throw new InternalAuthenticationServiceException("Invalid access token");
                }
                SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
            }
        }
        chain.doFilter(request, response);
    }
}
