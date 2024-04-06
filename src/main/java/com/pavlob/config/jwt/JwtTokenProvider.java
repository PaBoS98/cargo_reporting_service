package com.pavlob.config.jwt;

import com.pavlob.token.model.TokenLevel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String TOKEN_LEVEL_CLAIM_KEY = "tokenLevel";
    private static final String CLIENT_ID_CLAIM_KEY = "clientId";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    @Value("${jwt.algorithms}")
    private String jwtAlgorithm;

    public String generateClientToken(final String clientId) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(CLIENT_ID_CLAIM_KEY, clientId);
        claims.put(TOKEN_LEVEL_CLAIM_KEY, TokenLevel.CLIENT);

        return generateToken(claims);
    }

    private String generateToken(final Map<String, Object> claims) {
        log.info("generate token by claims {}", claims);
        final Instant now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtExpirationMs)))
                .signWith(new SecretKeySpec(jwtSecret.getBytes(), jwtAlgorithm))
                .compact();
    }

    public boolean isJwtTokenValid(final String jwt) {
        return !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Claims extractClaims(final String token) {
        return Jwts.parser()
                .verifyWith(new SecretKeySpec(jwtSecret.getBytes(), jwtAlgorithm))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public TokenLevel getTokenLevel(final String jwt) {
        final Claims claims = extractClaims(jwt);

        return getTokenLevel(claims);
    }

    private TokenLevel getTokenLevel(final Claims claims) {
        final String tokenLevel = extractClaimByKey(claims, TOKEN_LEVEL_CLAIM_KEY, String.class);

        return TokenLevel.valueOf(tokenLevel);
    }

    public  <T> T extractClaimByKey(final String jwt, final String key, final Class<T> clazz) {
        final Claims claims = extractClaims(jwt);
        return extractClaim(claims, (k) -> claims.get(key, clazz));
    }

    private <T> T extractClaimByKey(final Claims claims, final String key, final Class<T> clazz) {
        return extractClaim(claims, (k) -> claims.get(key, clazz));
    }

    private <T> T extractClaim(final String jwt, final Function<Claims, T> claimsResolvers) {
        final Claims claims = extractClaims(jwt);
        return claimsResolvers.apply(claims);
    }

    private <T> T extractClaim(final Claims claims, final Function<Claims, T> claimsResolvers) {
        return claimsResolvers.apply(claims);
    }
}
