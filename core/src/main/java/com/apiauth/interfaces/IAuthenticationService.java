package com.apiauth.interfaces;

import java.time.Instant;

import org.springframework.security.core.Authentication;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface IAuthenticationService {
    
    String generateToken(Authentication authentication, Instant instant, boolean isRefresh);

    String generateRefreshToken(Authentication authentication);

    String refreshToken(String refreshToken, boolean increaseTime);

    boolean isRefreshToken(String refreshToken);

    DecodedJWT decodeToken(String token);

    boolean isNonExpired(String token);

}
