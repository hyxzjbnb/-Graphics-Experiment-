package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.MyUserDetails;
import com.hyx.webflux.javaee.repository.MyUserDetailsRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtSigner {

    private final MyUserDetailsRepository myUserRepository;

    private final String key = "justAJwtSingleKey";
    private final String authorities = "authorities";
    private final String issuer = "identity";
    private final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.expiration.duration}")
    private int duration ;


    public String getAuthoritiesTag () {
        return authorities;
    }

    public String getIssuerTag () {
        return issuer;
    }

    public String getTokenPrefix () {
        return TOKEN_PREFIX;
    }

    public String generateToken (String username) {

        return generateToken(Objects.requireNonNull(myUserRepository.findByUsername(username).block()));
    }

    public String generateToken (MyUserDetails user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(authorities, user.getAuthorities())
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuer(issuer)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(duration))))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .compact();
    }

    public Claims parseToken (String token) {
        log.info("token : {}", token);
        return Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
