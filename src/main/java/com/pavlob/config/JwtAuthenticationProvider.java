package com.pavlob.config;

import com.pavlob.config.jwt.JwtTokenProvider;
import com.pavlob.token.model.JwtToken;
import com.pavlob.token.model.TokenLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;

import static com.pavlob.Constant.BEARER_PREFIX;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationProvider(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String token = (String) authentication.getPrincipal();
        if (!StringUtils.hasText(token)) {
            log.error("token {} is invalid", token);
            throw new BadCredentialsException("Invalid access token");
        }

        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken;
        try {
            final TokenLevel tokenLevel = jwtTokenProvider.getTokenLevel(token);
            if (tokenLevel.equals(TokenLevel.CLIENT)) {
                //TODO add specific validation for client token

            } else {
                preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(null, token);
                preAuthenticatedAuthenticationToken.setAuthenticated(Boolean.FALSE);
                return preAuthenticatedAuthenticationToken;
            }

            final JwtToken jwtTokenModel = new JwtToken();
            jwtTokenModel.setJwt(token);
            jwtTokenModel.setType(BEARER_PREFIX);
            jwtTokenModel.setTokenLevel(tokenLevel);

            preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(jwtTokenModel, token);
            preAuthenticatedAuthenticationToken.setAuthenticated(Boolean.TRUE);
            return preAuthenticatedAuthenticationToken;
        } catch (final Exception e) {
            preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(null, token);
            preAuthenticatedAuthenticationToken.setAuthenticated(Boolean.FALSE);
            return preAuthenticatedAuthenticationToken;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
