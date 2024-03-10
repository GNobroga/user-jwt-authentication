package com.apiauth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.apiauth.configuration.JwtConfig;
import com.apiauth.exceptions.TokenExpiredException;
import com.apiauth.exceptions.TokenProcessingException;
import com.apiauth.interfaces.IAuthenticationService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final JwtConfig jwtConfig;
    private Algorithm algorithm;

    @PostConstruct
    void init() {
        algorithm = Algorithm.RSA256(jwtConfig.getPublicKey(), jwtConfig.getPrivateKey());
    }

    @Override
    public String generateToken(Authentication authentication, Instant instant, boolean isRefresh) {
        try { 
            var roles = authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(","));
            var jwt = JWT.create()    
                .withIssuer(jwtConfig.getIssuer())
                .withExpiresAt(instant.plusSeconds(jwtConfig.getSecondsToExpiry()))
                .withSubject(authentication.getName())
                .withClaim("roles", roles);
          
            if (isRefresh) {
                jwt.withClaim("refreshToken", true);
            }

            return jwt.sign(algorithm);
        } catch (JWTCreationException | IllegalArgumentException ex) {
            throw new TokenProcessingException();
        }
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, Instant.now()
            .plusSeconds(jwtConfig.getSecondsToExpiry())
            .plus(2, ChronoUnit.HOURS), true);
    }

    @Override
    public DecodedJWT decodeToken(String token) {
       try {
            var jwtVerifier = JWT.require(algorithm)
                .withIssuer(jwtConfig.getIssuer())
                .build();
            var decodedJwt = jwtVerifier.verify(token);
            return decodedJwt;
       } catch (JWTVerificationException ex) {
            throw new TokenProcessingException();
       }
    }

    @Override
    public boolean isNonExpired(String token) {
        try {
            return JWT.decode(token).getExpiresAt().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String refreshToken(String refreshToken, boolean increaseTime) {
        try {
            if (isNonExpired(refreshToken)) {

                var time = Instant.now().plusSeconds(jwtConfig.getSecondsToExpiry());

                var decoded = decodeToken(refreshToken);

                var jwt = JWT.create()    
                    .withIssuer(jwtConfig.getIssuer())
                    .withExpiresAt(time)
                    .withSubject(decoded.getSubject())
                    .withClaim("roles",  decoded.getClaim("roles").asString());

                if (increaseTime) {
                    time.plus(2, ChronoUnit.HOURS);
                    jwt.withExpiresAt(time.plus(2, ChronoUnit.HOURS));
                    jwt.withClaim("refreshToken", true);
                }

                return jwt.sign(algorithm);
            }
            throw new TokenExpiredException();
        } catch (TokenExpiredException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } 
    }

    @Override
    public boolean isRefreshToken(String refreshToken) {
        return isNonExpired(refreshToken) && !decodeToken(refreshToken).getClaim("refreshToken").isMissing();
    }

}
